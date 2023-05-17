package com.example.demo.Controller.input;

import java.util.Date;

public class CustomerInput {
    private String address;
    private String birthday;
    private Integer customerId;
    private int districtId;
    private String email;
    private String name;
    private String password;
    private String phone;
    private int provinceId;
    private int wardId;

    public CustomerInput(String address, String birthday, Integer customerId, int districtId, String email, String name, String password, String phone, int provinceId, int wardId) {
        this.address = address;
        this.birthday = birthday;
        this.customerId = customerId;
        this.districtId = districtId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.provinceId = provinceId;
        this.wardId = wardId;
    }

    public CustomerInput() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }
}
