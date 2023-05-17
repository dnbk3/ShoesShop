package vn.btl.hdvauthenservice.data.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OtpVerificationResponse {
    private Boolean isSuccessVerified;
    private String username;
    private Integer userId;
    private String type;
}
