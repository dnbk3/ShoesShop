package vn.btl.hdvauthenservice.data.request;

import lombok.Data;

@Data
public class UpdatePasswordWithOTPRequest {

    private String username;
    private String newPassword;
    private String otpReferenceId;
}
