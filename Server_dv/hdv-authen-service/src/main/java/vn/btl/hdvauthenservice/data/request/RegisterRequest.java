package vn.btl.hdvauthenservice.data.request;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String username;
    private String name;
    private String password;
}
