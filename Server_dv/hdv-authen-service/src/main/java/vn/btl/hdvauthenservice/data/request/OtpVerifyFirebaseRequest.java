package vn.btl.hdvauthenservice.data.request;

import lombok.Getter;

@Getter
public class OtpVerifyFirebaseRequest {

    private String identity;

    private String idToken;

    private String appId;
}
