package vn.btl.hdvauthenservice.service;

import org.springframework.http.ResponseEntity;
import vn.btl.hdvauthenservice.data.request.ChangePasswordRequest;
import vn.btl.hdvauthenservice.data.request.LoginRequest;
import vn.btl.hdvauthenservice.data.request.RegisterRequest;
import vn.btl.hdvauthenservice.data.request.UpdatePasswordWithOTPRequest;
import vn.btl.hdvauthenservice.data.response.LoginResponse;



public interface UserService {
    LoginResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    void forgetPassword(UpdatePasswordWithOTPRequest request);

    void changePassword(ChangePasswordRequest request);

    void logout(Long userId, String token);

    ResponseEntity deleteUser(Long userId);

}
