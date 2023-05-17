package vn.btl.hdvauthenservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.btl.hdvauthenservice.config.OtpConfiguration;
import vn.btl.hdvauthenservice.data.request.OtpGenerationRequest;
import vn.btl.hdvauthenservice.data.request.OtpRegenerationRequest;
import vn.btl.hdvauthenservice.data.request.OtpVerifyRequest;
import vn.btl.hdvauthenservice.data.response.OtpResponse;
import vn.btl.hdvauthenservice.data.sql.model.OtpEntity;
import vn.btl.hdvauthenservice.data.sql.model.OtpRestrictionEntity;
import vn.btl.hdvauthenservice.data.sql.model.UserEntity;
import vn.btl.hdvauthenservice.data.sql.repository.OtpRepository;
import vn.btl.hdvauthenservice.data.sql.repository.OtpRestrictionRepository;
import vn.btl.hdvauthenservice.data.sql.repository.UserRepository;
import vn.btl.hdvauthenservice.exceptions.AuthenErrorCode;
import vn.btl.hdvauthenservice.exceptions.AuthenException;
import vn.btl.hdvauthenservice.service.OtpService;
import vn.btl.hdvauthenservice.service.SendOtpService;
import vn.btl.hdvauthenservice.utils.IdentityType;
import vn.btl.hdvauthenservice.utils.IdentityUtil;
import vn.btl.hdvauthenservice.utils.OtpTypeEnum;
import vn.btl.hdvauthenservice.utils.ValidateUtils;

import java.util.*;

@Service
@Slf4j
public class OtpServiceImpl implements OtpService {

    private Random rand = new Random();
    @Autowired
    private OtpConfiguration otpConfiguration;

    @Autowired
    OtpRestrictionRepository otpRestrictionRepository;

    @Autowired
    OtpRepository otpRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SendOtpService sendOtpService;

    @Autowired
    ValidateUtils validateUtils;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public OtpResponse generateOtp(OtpGenerationRequest request) {

        IdentityType identityType = IdentityUtil.getIdentityType(request.getUsername());
        checkIdentity(request.getUsername());
        if (verifyGenOtp(request.getUsername(), identityType, request.getType())) {
            createGenOtpRestriction(null, request.getUsername(), request.getType());
            throw new AuthenException(AuthenErrorCode.MAXIMUM_OTP_GENERATION_REACHED);
        }
        return sendOtp(request.getUsername(), identityType, request.getType());
    }


    @Override
    public OtpResponse regenerateOtp(OtpRegenerationRequest request) {
        List<OtpEntity> listOtp = otpRepository.findByOtpReferenceIdAndDeletedIsFalse(request.getOtpReferenceId());
        OtpEntity oldOtp = null;
        if (!listOtp.isEmpty())
            oldOtp = listOtp.get(0);
        else
            throw new AuthenException(AuthenErrorCode.OTP_REFERENCE_ID_NOT_FOUND);
        oldOtp.setDeleted(Boolean.TRUE);
        oldOtp.setUpdatedDate(new Date());

        OtpEntity regenerateOtpEntity = buildOtpEntity(
                oldOtp.getIdentity(),
                oldOtp.getSendType(),
                oldOtp.getOtpReferenceId(),
                oldOtp.getType());

        IdentityType identityType = IdentityUtil.getIdentityType(regenerateOtpEntity.getIdentity());

        log.info("Regenerating new otp for [{}] otpReferenceId", oldOtp.getOtpReferenceId());
        otpRepository.saveAll(Arrays.asList(oldOtp, regenerateOtpEntity));
        log.info("Regenerated new otp for [{}] otpReferenceId successfully", oldOtp.getOtpReferenceId());

        if (sendOtp(regenerateOtpEntity.getId(), regenerateOtpEntity.getIdentity(), identityType, regenerateOtpEntity.getOtpCode(), regenerateOtpEntity.getType()))
            return getOtpResponse(regenerateOtpEntity);
        throw new AuthenException(AuthenErrorCode.INVALID_OTP_TYPE, "Không thể gửi OTP!");
    }

    @Override
    public boolean verifyOtp(OtpVerifyRequest request) {
        if (request.getOtpReferenceId() == null || request.getOtpCode() == null) {
            log.error("Otp_reference_id and otp_code must be specified");
            throw new AuthenException(AuthenErrorCode.OTP_VERIFICATION_FAIL);
        }

        List<OtpEntity> listOtp = otpRepository.findByOtpReferenceIdAndDeletedIsFalse(request.getOtpReferenceId());
        if (listOtp.isEmpty()) {
            log.error("Cannot find OTP with [{}] otpReferenceId", request.getOtpReferenceId());
            throw new AuthenException(AuthenErrorCode.OTP_REFERENCE_ID_NOT_FOUND);
        }

        if (listOtp.size() > 1) {
            log.error("Fatal error. There're more than 1 active OTP for one [{}] otpReferenceId", request.getOtpReferenceId());
            throw new AuthenException(AuthenErrorCode.GENERAL_ERROR);
        }

        OtpEntity otp = listOtp.get(0);
        if (isOtpVerified(otp)) {
            log.error("OTP with [{}] otpReferenceId has been verified already", request.getOtpReferenceId());
            throw new AuthenException(AuthenErrorCode.OTP_USED);
        }

        if (isOtpExpired(otp)) {
            log.error("OTP with [{}] otpReferenceId has expired", request.getOtpReferenceId());
            throw new AuthenException(AuthenErrorCode.OTP_EXPIRED);
        }

        if (!isOtpCodeMatch(otp, request.getOtpCode())) {
            log.error("OTP code does not match");
            throw new AuthenException(AuthenErrorCode.OTP_VERIFICATION_FAIL);
        }
        log.info("Verify OTP with [{}] otpReferenceId successful", request.getOtpReferenceId());
        otp.setVerified(true);
        otp.setVerifiedDate(new Date());
        otp.setUpdatedDate(new Date());
        otpRepository.save(otp);
        return true;
    }

    public boolean verifyOtp(String identity, String otpReferenceId) {
        if (otpReferenceId == null || identity == null) {
            log.error("Otp_reference_id and otp_code must be specified");
            throw new AuthenException(AuthenErrorCode.OTP_VERIFICATION_FAIL);
        }

        List<OtpEntity> listOtp = otpRepository.findByOtpReferenceIdAndDeletedIsFalse(otpReferenceId);
        if (listOtp.isEmpty()) {
            log.error("Cannot find OTP with [{}] otpReferenceId", otpReferenceId);
            throw new AuthenException(AuthenErrorCode.OTP_REFERENCE_ID_NOT_FOUND);
        }

        if (listOtp.size() > 1) {
            log.error("Fatal error. There're more than 1 active OTP for one [{}] otpReferenceId", otpReferenceId);
            throw new AuthenException(AuthenErrorCode.GENERAL_ERROR);
        }

        OtpEntity otp = listOtp.get(0);
        if (otp.isUsed()) {
            log.error("OTP with [{}] otpReferenceId has been verified already", otpReferenceId);
            throw new AuthenException(AuthenErrorCode.OTP_USED);
        }

        if (isOtpExpired(otp)) {
            log.error("OTP with [{}] otpReferenceId has expired", otpReferenceId);
            throw new AuthenException(AuthenErrorCode.OTP_EXPIRED);
        }

        if (!otp.getIdentity().equals(identity)) {
            log.error("Otp is not belong to mobile number");
            throw new AuthenException(AuthenErrorCode.OTP_VERIFICATION_FAIL);
        }
        otp.setUsed(true);
        otp.setDeleted(true);
        otp.setUpdatedDate(new Date());
        otpRepository.save(otp);

        return true;
    }



    private String genOtpCode(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(10);
            builder.append(otpConfiguration.getOtpValidCharacter().charAt(index));
        }
        return builder.toString();
    }

    private String genOtpCode() {
        String otpCode;
        List<String> blacklist = otpConfiguration.getOtpBlacklist();

        do {
            otpCode = genOtpCode(otpConfiguration.getOtpLength());
        } while (blacklist.contains(otpCode));

        return otpCode;
    }

    public String genOtpReferenceId() {
        return UUID.randomUUID().toString();
    }

    private OtpEntity buildOtpEntity(String identity, String sendType, String otpReferenceId, String otpType) {
        OtpEntity otpEntity = new OtpEntity();

        otpEntity.setIdentity(identity);
        otpEntity.setOtpReferenceId(otpReferenceId);
        otpEntity.setSendType(sendType);
        otpEntity.setType(otpType);
        otpEntity.setOtpCode(genOtpCode());

        otpEntity.setDeleted(false);
        otpEntity.setVerified(false);

        Date now = new Date();
        otpEntity.setExpiredDate(new Date(now.getTime() + otpConfiguration.getOtpLifeTimeInSecond() * 1000));
        otpEntity.setCreatedDate(now);
        otpEntity.setUpdatedDate(now);
        return otpEntity;
    }

    private OtpResponse getOtpResponse(OtpEntity otpEntity) {
        OtpResponse res = new OtpResponse();
        res.setOtpReferenceId(otpEntity.getOtpReferenceId());
        res.setExpiredDate(otpEntity.getExpiredDate());
        res.setCreatedDate(otpEntity.getCreatedDate());
        return res;
    }

    private void verifyPhoneRegistration(String phone, String type) {
        UserEntity custom = userRepository.findOneByPhoneAndActiveIsTrue(phone);
        if (OtpTypeEnum.REGISTRATION.getValue().equals(type)) {
            if (custom != null && custom.getPassword() != null) {
                log.error("Phone number [{}] already register before", phone);
                throw new AuthenException(AuthenErrorCode.MOBILE_REGISTERED);
            }
        } else if (OtpTypeEnum.FORGOT_PASSWORD.getValue().equals(type)) {
            if (custom == null) {
                log.error("Phone [{}] not found", phone);
                throw new AuthenException(AuthenErrorCode.MOBILE_NOT_REGISTERED);
            }
        }
    }

    private void verifyEmailRegistration(String email, String type) {
        UserEntity custom = userRepository.findOneByEmailAndActiveIsTrue(email);
        if (OtpTypeEnum.REGISTRATION.getValue().equals(type)) {
            if (custom != null && custom.getPassword() != null) {
                log.error("Email [{}] already register before", email);
                throw new AuthenException(AuthenErrorCode.EMAIL_REGISTERED);
            }
        } else if (OtpTypeEnum.FORGOT_PASSWORD.getValue().equals(type)) {
            if (custom == null) {
                log.error("Email [{}] not found", email);
                throw new AuthenException(AuthenErrorCode.EMAIL_NOT_REGISTERED);
            }
        }
    }


    private boolean verifyGenOtp(String username, IdentityType identityType, String type) {

        if (identityType == IdentityType.EMAIL) {
            verifyEmailRegistration(username, type);
        } else if (identityType == IdentityType.PHONE) {
            verifyPhoneRegistration(username, type);
        }

        Date time = new Date(System.currentTimeMillis() - otpConfiguration.getGenerationRegisterTimeBoxInMinute() * 60 * 1000L);

        long timesCreateOTP = otpRepository.countGeneratedOtpByIdentityAndDeleted(username, type, time);

        return timesCreateOTP >= otpConfiguration.getGenerationTimesLimit();
    }

    private OtpRestrictionEntity createGenOtpRestriction(Long userId, String identity, String type) {
        OtpRestrictionEntity entity = new OtpRestrictionEntity();
        entity.setUserId(userId);
        entity.setIdentity(identity);
        entity.setType(type);
        entity.setCreatedDate(new Date());
        entity.setUpdatedDate(new Date());
        return otpRestrictionRepository.save(entity);
    }

    private boolean sendOtp(Long otpId, String identity, IdentityType identityType, String otpCode, String sendType) {
        if (identityType == IdentityType.EMAIL) {
            return sendOtpService.sendToEmail(otpId, identity, otpCode, sendType);
        } else if (identityType == IdentityType.PHONE) {
            return sendOtpService.sendToPhone(otpId, identity, otpCode, sendType);
        }
        return false;
    }


    private boolean isOtpVerified(OtpEntity otp) {
        return otp.isVerified();

    }

    private boolean isOtpExpired(OtpEntity otp) {
        return otp.getExpiredDate().before(new Date());
    }

    private boolean isOtpCodeMatch(OtpEntity otp, String otpCode) {
        return otp.getOtpCode().equals(otpCode);
    }

    private void checkIdentity(String username) {
        IdentityType identityType = IdentityUtil.getIdentityType(username);

        if (identityType == IdentityType.PHONE) {
            log.info("Verifying user create request");
            validateUtils.mobileNumberVerify(username);
        } else if (identityType == IdentityType.EMAIL) {
            validateUtils.emailVerify(username);
        }
    }

    private OtpResponse sendOtp(String username, IdentityType identityType, String type) {
        OtpEntity otpEntity = buildOtpEntity(
                username,
                identityType.getValue(),
                genOtpReferenceId(),
                type);

        log.info("Generating new otp");
        otpEntity = otpRepository.save(otpEntity);
        log.info("Generated otp with [{}] otpReferenceId successfully", otpEntity.getOtpReferenceId());

        if (sendOtp(otpEntity.getId(), otpEntity.getIdentity(), identityType, otpEntity.getOtpCode(), type))
            return getOtpResponse(otpEntity);
        throw new AuthenException(AuthenErrorCode.INVALID_OTP_TYPE, "Không thể gửi OTP!");
    }

}
