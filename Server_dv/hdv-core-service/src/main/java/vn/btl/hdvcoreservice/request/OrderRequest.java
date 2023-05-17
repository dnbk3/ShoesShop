package vn.btl.hdvcoreservice.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    String note;
    String address;
    String phone;
    String paymentMethod;
    List<ProductOrderRequest> requests;
}
