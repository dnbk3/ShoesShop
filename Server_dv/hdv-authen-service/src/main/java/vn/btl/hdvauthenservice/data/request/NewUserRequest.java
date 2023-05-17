package vn.btl.hdvauthenservice.data.request;

import lombok.Data;

@Data
public class NewUserRequest {
    private String username;
    private String fullname;
    private String appId;
}
