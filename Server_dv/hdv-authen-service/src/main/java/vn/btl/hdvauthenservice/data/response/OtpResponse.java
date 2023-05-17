package vn.btl.hdvauthenservice.data.response;

import lombok.Data;

import java.util.Date;

@Data
public class OtpResponse {
    private String otpReferenceId;
    private Date createdDate;
    private Date expiredDate;
}


