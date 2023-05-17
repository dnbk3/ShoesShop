package vn.btl.hdvauthenservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.btl.hdvauthenservice.data.request.ChangePasswordRequest;
import vn.btl.hdvauthenservice.data.request.LoginRequest;
import vn.btl.hdvauthenservice.data.request.RegisterRequest;
import vn.btl.hdvauthenservice.data.request.UpdatePasswordWithOTPRequest;
import vn.btl.hdvauthenservice.data.response.LoginResponse;
import vn.btl.hdvauthenservice.interceptor.Payload;
import vn.btl.hdvauthenservice.service.UserService;
import vn.btl.hdvauthenservice.utils.ResponseFactory;


@RestController
@RequestMapping("/v2/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity registerUser(@RequestBody RegisterRequest request) {
        log.info("========== Start to create a user  ==========");
        ResponseEntity response;
        log.info("Create a user with [{}] username", request.getUsername());

        LoginResponse loginResponse = userService.register(request);
        response = ResponseFactory.success(loginResponse, LoginResponse.class);

        log.info("========== End to create a user  ==========");
        return response;
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginRequest request) {
        log.info("========== Start login user  ==========");
        ResponseEntity response;
        log.info("user [{}] login", request.getUsername());
        LoginResponse data = userService.login(request);
        response = ResponseFactory.success(data, LoginResponse.class);
        log.info("========== End login user ==========");
        return response;
    }

    @PutMapping(value = "/change-password")
    public ResponseEntity changePassword(@RequestBody ChangePasswordRequest request) {
        log.info("========== Start change password  ==========");
        ResponseEntity response;
        log.info("Customer [{}] change password", request.getUsername());
        userService.changePassword(request);
        response = ResponseFactory.success();
        log.info("========== End change password  ==========");
        return response;
    }

    @PostMapping(value = "/forget-password")
    public ResponseEntity updatePasswordWithOTP(@RequestBody UpdatePasswordWithOTPRequest request) {
        log.info("========== Start to reset pass for user  ==========");
        ResponseEntity response;
        log.info("Update password for [{}] username with [{}] otpReferenceId", request.getUsername(), request.getOtpReferenceId());
        userService.forgetPassword(request);

        response = ResponseFactory.success();
        log.info("========== End to reset pass for customer ==========");
        return response;
    }

    @PostMapping(value = "/logout")
    public ResponseEntity logout(@RequestAttribute Payload payload) {
        log.info("Logout user: {} {}", payload.getCustomerId(), payload.getPhone());
        userService.logout(payload.getCustomerId(), payload.getToken());
        return ResponseFactory.success();
    }

    @DeleteMapping("/{userId:\\d+}")
    public ResponseEntity deleteUser(@PathVariable Long userId){
        log.info("delete user id {}", userId);
        return userService.deleteUser(userId);
    }

}
