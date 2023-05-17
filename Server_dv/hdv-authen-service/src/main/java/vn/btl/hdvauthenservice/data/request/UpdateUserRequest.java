package vn.btl.hdvauthenservice.data.request;

import lombok.Getter;

@Getter
public class UpdateUserRequest {
    private String username;
    private String email;
    private String phone;
    private Long userId;

}
