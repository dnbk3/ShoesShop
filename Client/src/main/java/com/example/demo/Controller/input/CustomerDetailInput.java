package com.example.demo.Controller.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailInput {

    private Integer customerId;
    private String name;
    private String phone;
    private String email;
    private String password;
    private boolean active;
    private String fullName;
    private long birthday;
    private String address;
    private int districtId;
    private String districtName;
    private int provinceId;
    private String provinceName;
    private int wardId;
    private String wardName;
    private long createdDate;
    private long modifiedDate;
    private String token;
    private String tokenExpire;
    private int countryCode;


}
