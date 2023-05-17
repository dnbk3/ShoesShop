package vn.btl.hdvauthenservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.btl.hdvauthenservice.config.OtpConfiguration;
import vn.btl.hdvauthenservice.data.redis.TokenRedisRepository;
import vn.btl.hdvauthenservice.data.request.*;
import vn.btl.hdvauthenservice.data.response.LoginResponse;
import vn.btl.hdvauthenservice.data.sql.model.UserEntity;
import vn.btl.hdvauthenservice.data.sql.repository.UserRepository;
import vn.btl.hdvauthenservice.exceptions.AuthenErrorCode;
import vn.btl.hdvauthenservice.exceptions.AuthenException;
import vn.btl.hdvauthenservice.service.OtpService;
import vn.btl.hdvauthenservice.service.UserService;
import vn.btl.hdvauthenservice.utils.*;


import java.sql.Timestamp;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Value("${authen.chat.secret.key}")
    String secretKey;

    @Value("${token.expired.time.in.minutes}")
    private Integer expiredTimeInMinutes;

    @Autowired
    OtpConfiguration otpConfiguration;

    @Autowired
    OtpService otpService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    ValidateUtils validateUtils;

    @Autowired
    private TokenRedisRepository tokenRedisRepository;


    @Override
    public LoginResponse register(RegisterRequest request) {
        IdentityType identityType = IdentityUtil.getIdentityType(request.getUsername());

        if (identityType == IdentityType.PHONE) {
            log.info("Verifying user create request");
            validateUtils.mobileNumberVerify(request.getUsername());
        } else if (identityType == IdentityType.EMAIL) {
            validateUtils.emailVerify(request.getUsername());
        }

        String password = request.getPassword();
        validateUtils.passwordVerify(password);
        log.info("Customer create request is verified");

        UserEntity entity = null;
        if (identityType == IdentityType.PHONE) {
            entity = userRepository.findOneByPhoneAndActiveIsTrue(request.getUsername());
        } else if (identityType == IdentityType.EMAIL) {
            entity = userRepository.findOneByEmailAndActiveIsTrue(request.getUsername());
        }

        if (entity != null && entity.isVerified()) {
            log.info("Username đã được đăng ký {}", request.getUsername());
            throw new AuthenException(AuthenErrorCode.ENTITY_EXISTED);
        }

        if (entity == null) {
            entity = new UserEntity();
        }
        if (identityType == IdentityType.PHONE) {
            entity.setPhone(request.getUsername());
        } else if (identityType == IdentityType.EMAIL) {
            entity.setEmail(request.getUsername());
        }

        entity.setName(request.getName());

        entity.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));

        Date now = new Date();
        entity.setCreatedDate(now);
        entity.setModifiedDate(now);
        entity.setActive(true);
        entity.setVerified(true);

        UserEntity user = userRepository.save(entity);

        requestAddNewUser(user);
        return makeLoginResponse(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        IdentityType identityType = IdentityUtil.getIdentityType(request.getUsername());

        if (identityType == IdentityType.PHONE) {
            validateUtils.mobileNumberVerify(request.getUsername());
        } else if (identityType == IdentityType.EMAIL) {
            validateUtils.emailVerify(request.getUsername());
        }
        validateUtils.passwordVerifyLogin(request.getPassword());
        UserEntity entity = null;
        if (identityType == IdentityType.PHONE) {
            entity = userRepository.findOneByPhoneAndActiveIsTrue(request.getUsername());
        } else if (identityType == IdentityType.EMAIL) {
            entity = userRepository.findOneByEmailAndActiveIsTrue(request.getUsername());
        }
        if (entity == null) {
            log.error("Customer with [{}] mobile number is not existed", request.getUsername());
            throw new AuthenException(AuthenErrorCode.ENTITY_NOT_EXISTS);
        }
        if (!entity.isActive()) {
            log.error("Customer [{}] is inactive", request.getUsername());
            throw new AuthenException(AuthenErrorCode.USER_INACTIVE);
        }
        boolean check = BCrypt.checkpw(request.getPassword(), entity.getPassword());
        if (check) {
            return makeLoginResponse(entity);
        } else {
            log.error("Password is not match");
            throw new AuthenException(AuthenErrorCode.INVALID_USER_PASS);
        }
    }

    @Override
    public void forgetPassword(UpdatePasswordWithOTPRequest request) {
        IdentityType identityType = IdentityUtil.getIdentityType(request.getUsername());

        if (request.getUsername() == null ||
                request.getNewPassword() == null ||
                request.getOtpReferenceId() == null) {
            log.error("Update password without required fields");
            throw new AuthenException(AuthenErrorCode.INVALID_REQUEST);
        }

        UserEntity entity = null;
        if (identityType == IdentityType.PHONE) {
            entity = userRepository.findOneByPhoneAndActiveIsTrue(request.getUsername());
        } else if (identityType == IdentityType.EMAIL) {
            entity = userRepository.findOneByEmailAndActiveIsTrue(request.getUsername());
        }
        if (entity == null) {
            log.error("Customer with [{}] username is not existed", entity.getUsername());
            throw new AuthenException(AuthenErrorCode.ENTITY_NOT_EXISTS);
        }
        String newPassword = request.getNewPassword();
        validateUtils.passwordVerify(newPassword);
        otpService.verifyOtp(request.getUsername(), request.getOtpReferenceId());


        entity.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(entity);
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        IdentityType identityType = IdentityUtil.getIdentityType(request.getUsername());
        UserEntity entity = null;
        if (identityType == IdentityType.PHONE) {
            entity = userRepository.findOneByPhoneAndActiveIsTrue(request.getUsername());
        } else if (identityType == IdentityType.EMAIL) {
            entity = userRepository.findOneByEmailAndActiveIsTrue(request.getUsername());
        }
        if (entity == null) {
            log.error("Can't do this action with customer {}", request.getUsername());
            throw new AuthenException(AuthenErrorCode.ENTITY_NOT_EXISTS);
        }
        boolean check = BCrypt.checkpw(request.getOldPassword(), entity.getPassword());
        if (!check) {
            log.error("Password is not match");
            throw new AuthenException(AuthenErrorCode.OLD_PASSWORD_NOT_VALID);
        }
        if (request.getOldPassword().equals(request.getNewPassword())) {
            log.error("New password isn't equal old password");
            throw new AuthenException(AuthenErrorCode.PASS_NOT_EQUAL);
        }
        validateUtils.passwordVerify(request.getNewPassword());
        entity.setPassword(new BCryptPasswordEncoder().encode(request.getNewPassword()));
        entity.setModifiedDate(new Date());
        userRepository.save(entity);
    }

    @Override
    public void logout(Long userId, String token) {
        Boolean deleted = tokenRedisRepository.deleteToken(userId, token);
        log.info("Delete all token of user {}: {}", userId, deleted);
    }

    @Override
    public ResponseEntity deleteUser(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new AuthenException(AuthenErrorCode.ENTITY_NOT_EXISTS));
        userRepository.delete(user);
        return ResponseFactory.success();
    }

    private LoginResponse makeLoginResponse(UserEntity entity) {
        LoginResponse res = new LoginResponse();

        long delta_time = 60L * 1000 * expiredTimeInMinutes;

        long now = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(now);
        Timestamp expiredTime = new Timestamp(now + delta_time);
        Map<String, Object> info = new HashMap<>();
        info.put("user_id", String.valueOf(entity.getCustomerId()));
        info.put("phone", entity.getPhone());
        info.put("email", entity.getEmail());
        String token = jwtUtil.generateToken(String.valueOf(entity.getCustomerId()), Collections.emptyList(), timestamp.getTime(), expiredTime.getTime(), info);
        tokenRedisRepository.add(String.valueOf(entity.getCustomerId()), token);

        res.setCustomerId(entity.getCustomerId());
        
        res.setName(entity.getName());

        res.setPhone(entity.getPhone());
        res.setEmail(entity.getEmail());

        res.setToken(token);
        res.setTokenExpire(expiredTime);
        res.setCreatedDate(timestamp);
        return res;
    }

    private ResponseEntity requestAddNewUser(UserEntity user) {

        Map<String, Object> object = new HashMap<>();
        object.put("customerId", user.getCustomerId());
        object.put("name", user.getName());
        object.put("phone", user.getPhone());
        object.put("email", user.getEmail());
        object.put("password", "******");

        String endPoint = "http://localhost:8002/core/customers";
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<>(object, requestHeader);
        log.info("Call gateway [{}] with body [{}]", endPoint, object);
        ResponseEntity response;
        try {
            response = restTemplate.exchange(
                    endPoint,
                    HttpMethod.POST,
                    httpEntity,
                    Object.class);
        } catch (Exception ex) {
            log.info("Can not connect to [{}], ex [{}]", endPoint, ex.getMessage());
            throw ex;
        }
        log.info("Received response from [{}] with code [{}]", endPoint, response.getStatusCode());
        return response;
    }

    private ResponseEntity requestAddNewUserS(UserEntity user) {

        Map<String, Object> object = new HashMap<>();
        object.put("username", user.getCustomerId());
        object.put("password", user.getName());

        String endPoint = "localhost:8082/auth/v2/user/login";
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<>(object, requestHeader);
        log.info("Call gateway [{}] with body [{}]", endPoint, object);
        ResponseEntity response;
        try {
            response = restTemplate.exchange(
                    endPoint,
                    HttpMethod.POST,
                    httpEntity,
                    Object.class);
        } catch (Exception ex) {
            log.info("Can not connect to [{}], ex [{}]", endPoint, ex.getMessage());
            throw ex;
        }
        log.info("Received response from [{}] with code [{}]", endPoint, response.getStatusCode());
        if(response.getStatusCode() == HttpStatus.OK){
        }
        return response;
    }

}
