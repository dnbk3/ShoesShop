package vn.btl.hdvcoreservice.request;

import lombok.Data;

@Data
public class SupplierRequest {

    private String name;
    private String address;
    private String email;
    private String phone;
    private String paymentMethod;
}
