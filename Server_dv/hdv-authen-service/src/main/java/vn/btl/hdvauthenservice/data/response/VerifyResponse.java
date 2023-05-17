package vn.btl.hdvauthenservice.data.response;

import lombok.Data;

@Data
public class VerifyResponse {

    private String identity;

    private String userId;

    private Long timeExp;
}