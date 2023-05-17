package vn.btl.hdvcoreservice.response;

import lombok.Data;

@Data
public class OrderDetailResponse {

    private String productName;

    private String productImage;

    private long productPrice;

    private int quantity;

    private long amount;
}
