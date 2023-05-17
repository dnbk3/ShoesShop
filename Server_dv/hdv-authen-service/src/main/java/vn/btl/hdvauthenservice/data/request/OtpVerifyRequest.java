package vn.btl.hdvauthenservice.data.request;

import lombok.Getter;

@Getter
public class OtpVerifyRequest {
    private String otpReferenceId;
    private String otpCode;
}

