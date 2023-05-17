package vn.btl.hdvcoreservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import vn.btl.hdvcoreservice.exception.CoreErrorCode;
import vn.btl.hdvcoreservice.exception.CoreException;
import vn.btl.hdvcoreservice.interceptor.Payload;
import vn.btl.hdvcoreservice.repository.SystemCustomerDao;
import vn.btl.hdvcoreservice.repository.UserRoleDao;
import vn.btl.hdvcoreservice.repository.entity.SystemCustomer;
import vn.btl.hdvcoreservice.repository.entity.UserRole;
import vn.btl.hdvcoreservice.request.NewSystemCustomerRequest;
import vn.btl.hdvcoreservice.request.UpdateSystemCustomerRequest;
import vn.btl.hdvcoreservice.service.CustomerService;
import vn.btl.hdvcoreservice.service.UserRoleService;
import vn.btl.hdvcoreservice.utils.AppUtils;
import vn.btl.hdvcoreservice.utils.PageResponse;
import vn.btl.hdvcoreservice.utils.PageResponseUtil;
import vn.btl.hdvcoreservice.utils.ResponseFactory;

import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private SystemCustomerDao customerDao;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserRoleDao userRoleDao;

    private final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public SystemCustomer addCustomer(NewSystemCustomerRequest request) {
        SystemCustomer entity = null;
        if (request.getPhone() != null){
            entity = customerDao.findOneByPhone(request.getPhone());
        } else if (request.getEmail() != null) {
            // TODO: check same email
            entity = customerDao.findOneByEmail(request.getEmail());
        }
        if (entity != null) {
            logger.info("Số điện thoại đã được đăng ký {}", request.getPhone());
            throw new CoreException(CoreErrorCode.ENTITY_EXISTED, "Số điện thoại đã được đăng ký");
        }
        entity = new SystemCustomer();

        AppUtils.copyPropertiesIgnoreNull(request, entity);

        entity.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));

        Date now = new Date();
        entity.setCreatedDate(now);
        entity.setModifiedDate(now);
        entity.setActive(true);

        SystemCustomer customer = customerDao.save(entity);

        try {
            userRoleService.addRoleUser(customer.getCustomerId(), 3);
        }
        catch (Exception e){
            logger.info(e.getMessage());
        }
        return customerDao.save(entity);
    }

    @Override
    public SystemCustomer updateCustomer(Long customerId, UpdateSystemCustomerRequest request) {
        SystemCustomer entity = customerDao.findOneByCustomerId(customerId);
        if (entity == null) {
            logger.info("Không tìm thấy người dùng {}", customerId);
            throw new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS, "Không tìm thấy người dùng");
        }

        AppUtils.copyPropertiesIgnoreNull(request, entity);

        Date now = new Date();
        entity.setModifiedDate(now);

        return customerDao.save(entity);
    }



    @Override
    public SystemCustomer getByCustomerId(Long customerId) {
        return customerDao.findOneByCustomerId(customerId);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        SystemCustomer customer = customerDao.findOneByCustomerId(customerId);
        if(customer == null)
            throw new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS);
        customerDao.delete(customer);
        List<UserRole> userRoles =  userRoleDao.findAllByUserId(customerId);
        userRoleDao.deleteAll(userRoles);
        String endPoint  = "http://localhost:8010/auth/v2/user/" + customerId;
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity response;
        try {
            response = restTemplate.exchange(
                    endPoint,
                    HttpMethod.DELETE,
                    null,
                    Object.class);
        } catch (Exception ex) {
            logger.info("Can not connect to [{}], ex [{}]", endPoint, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public ResponseEntity getAllCustomer(String keyword, Pageable pageable) {
        Page<SystemCustomer> customerPage = customerDao.findByPhoneContainsIgnoreCaseOrNameContainsIgnoreCaseOrEmailContainsIgnoreCase(keyword, keyword, keyword, pageable);
        logger.info("get all customer {}", keyword);
        PageResponse pageResponse = PageResponseUtil.buildPageResponse(customerPage);
        return ResponseFactory.success(customerPage.getContent(), pageResponse);
    }
}
