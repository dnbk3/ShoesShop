package vn.btl.hdvcoreservice.request;

import lombok.Data;

@Data
public class ProductOrderRequest {

    private Integer productId;
    private Integer number;
    private Integer size;
}
