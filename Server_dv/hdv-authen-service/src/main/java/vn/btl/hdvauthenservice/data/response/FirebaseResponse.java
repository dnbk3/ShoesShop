package vn.btl.hdvauthenservice.data.response;

import lombok.Data;

@Data
public class FirebaseResponse {

    private Integer statusCode;

    private String error;

    private VerifyResponse verifyResponse;
}
