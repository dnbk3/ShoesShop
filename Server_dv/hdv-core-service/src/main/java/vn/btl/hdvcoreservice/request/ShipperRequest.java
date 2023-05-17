package vn.btl.hdvcoreservice.request;

import lombok.Data;

@Data
public class ShipperRequest {

    private int shipperId;
    private String name;
    private String sex;
    private String companyName;
    private String phone;
}
