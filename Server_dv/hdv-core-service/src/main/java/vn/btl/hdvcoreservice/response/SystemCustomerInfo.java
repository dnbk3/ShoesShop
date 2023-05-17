package vn.btl.hdvcoreservice.response;

import lombok.Data;

import java.util.Date;

@Data
public class SystemCustomerInfo {
    private long customerId;
    private String name;
    private String phone;
    private String email;
    private boolean isActive;
    private Date birthday;
    private String address;
    private long provinceId;
    private long districtId;
    private long wardId;
    private Date createdDate;
    private Date modifiedDate;
    private String token;
    private Date tokenExpire;
    private String provinceName;
    private String districtName;
    private String wardName;
}
