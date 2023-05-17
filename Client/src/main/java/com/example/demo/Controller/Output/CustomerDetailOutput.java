package com.example.demo.Controller.Output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailOutput {

    private int customerId;
    private String name;
    private String phone;
    private String email;
    private String password;
    private boolean active;
    private String fullName;
    private String birthday;
    private String address;
    private int districtId;
    private String districtName;
    private int provinceId;
    private String provinceName;
    private int wardId;
    private String wardName;
    private String createdDate;
    private String modifiedDate;
    private String token;
    private String tokenExpire;
    private int countryCode;
}
