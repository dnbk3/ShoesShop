package vn.btl.hdvcoreservice.controller;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.btl.hdvcoreservice.interceptor.Payload;
import vn.btl.hdvcoreservice.repository.SystemRegionDao;
import vn.btl.hdvcoreservice.repository.entity.SystemCustomer;
import vn.btl.hdvcoreservice.repository.entity.SystemRegion;
import vn.btl.hdvcoreservice.repository.entity.UserRole;
import vn.btl.hdvcoreservice.request.NewSystemCustomerRequest;
import vn.btl.hdvcoreservice.request.UpdateSystemCustomerRequest;
import vn.btl.hdvcoreservice.response.SystemCustomerInfo;
import vn.btl.hdvcoreservice.response.UserRoleResponse;
import vn.btl.hdvcoreservice.service.CustomerService;
import vn.btl.hdvcoreservice.service.UserRoleService;
import vn.btl.hdvcoreservice.utils.AppUtils;
import vn.btl.hdvcoreservice.utils.Operator;
import vn.btl.hdvcoreservice.utils.ResponseFactory;

import java.util.List;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private SystemRegionDao regionDao;
    @Autowired
    private UserRoleService userRoleService;

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @PutMapping(value = "/{customerId}")
    public ResponseEntity updateSystemCustomer(@PathVariable("customerId") Long customerId,
                                               @RequestBody UpdateSystemCustomerRequest request,
                                               @RequestAttribute Payload payload) {
//        if (payload.getCustomerId().equals(customerId)) {
            SystemCustomer data = customerService.updateCustomer(customerId, request);
            return ResponseFactory.success(data, SystemCustomer.class);
//        }
//        return ResponseFactory.permissionDenied();
    }

    @PostMapping
    public ResponseEntity addNewSystemCustomer(@RequestBody NewSystemCustomerRequest request) {
        SystemCustomer entity = customerService.addCustomer(request);
        return ResponseFactory.success(entity, SystemCustomer.class);
    }

    @GetMapping(value = "/{customerId}")
    public ResponseEntity getSystemCustomerBySystemCustomerId(@PathVariable("customerId") Long customerId) {
        SystemCustomer data = customerService.getByCustomerId(customerId);
        return ResponseFactory.success(data, SystemCustomer.class);
    }

    @SneakyThrows
    @GetMapping(value = "/profile")
    public ResponseEntity getUserProfile(@RequestAttribute Payload payload) {
        logger.info("Received get profile admin request");
        SystemCustomer entity = customerService.getByCustomerId(payload.getCustomerId());
        SystemCustomerInfo customerInfo = new SystemCustomerInfo();
        AppUtils.copyPropertiesIgnoreNull(entity, customerInfo);
        logger.info("Received response: [{}]", customerInfo);
        return ResponseFactory.success(customerInfo, SystemCustomerInfo.class);
    }

    @SneakyThrows
    @GetMapping("/region")
    public ResponseEntity getRegion(@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword, @RequestParam Long parentId){
        logger.info("lấy danh sách tỉnh thành quận huyện");
        List<SystemRegion> regions = regionDao.findByItemNameContainsIgnoreCaseAndParentItemId(keyword,parentId);
        return ResponseFactory.success(regions, List.class);
    }

    @SneakyThrows
    @GetMapping("/role")
    public ResponseEntity getUserRole(@RequestAttribute Payload payload){
        List<UserRoleResponse> responses = userRoleService.getRoleUser(payload.getCustomerId());
        return ResponseFactory.success(responses,List.class);
    }

    @SneakyThrows
    @GetMapping("/role-user")
    public ResponseEntity getRoleUser(@RequestAttribute Payload payload, @RequestParam Long userId){
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.VIEW_USER_ROLE))
            return ResponseFactory.permissionDenied();
        List<UserRoleResponse> responses = userRoleService.getRoleUser(userId);
        return ResponseFactory.success(responses,List.class);
    }

    @SneakyThrows
    @PutMapping("/role-user")
    public ResponseEntity updateRoleUser(@RequestAttribute Payload payload, @RequestParam Long userId, @RequestParam Integer roleId){
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.UPDATE_USER_ROLE))
            return ResponseFactory.permissionDenied();
        UserRole userRole = userRoleService.updateUserRole(userId, roleId);
        return ResponseFactory.success(userRole, UserRole.class);
    }

    @SneakyThrows
    @GetMapping("/get-all")
    public ResponseEntity getAllCustomer(@RequestAttribute Payload payload
            , @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword
            , @RequestParam(value = "page", defaultValue = "0", required = false) Integer page
            , @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
            , @RequestParam(value = "direction", defaultValue = "DESC", required = false)String direction
            , @RequestParam(value = "orderBy", defaultValue = "customerId", required = false)String orderBy) {
        if (!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.VIEW_LIST_CUSTOMER))
            return ResponseFactory.permissionDenied();
        Pageable pageable = AppUtils.createPageable(page, pageSize, direction, orderBy);
        logger.info("get all customer");
        return customerService.getAllCustomer(keyword, pageable);
    }

    @DeleteMapping("/{customerId:\\d+}")
    public ResponseEntity deleteCustomerId(@RequestAttribute Payload payload, @PathVariable Long customerId){
        if (!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.DELETE_CUSTOMER))
            return ResponseFactory.permissionDenied();
        logger.info("delete customer {}", customerId);
        customerService.deleteCustomer(customerId);
        return ResponseFactory.success();
    }
}
