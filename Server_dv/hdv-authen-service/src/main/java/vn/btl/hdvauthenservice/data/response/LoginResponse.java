package vn.btl.hdvauthenservice.data.response;

import lombok.Data;

import java.util.Date;

@Data
public class LoginResponse {

    private String token;
    private long customerId;
    private String name;
    private String phone;
    private String email;
    private String avatar;
    private boolean isActive;
    private Date birthday;
    private Date createdDate;
    private Date modifiedDate;
    private Date tokenExpire;
    private String chatToken;
}
