package vn.btl.hdvauthenservice.data.request;

import lombok.Data;

@Data
public class UpdatePasswordFirebaseRequest {

    private String username;
    private String newPassword;
    private String idToken;
    private String appId;
}
