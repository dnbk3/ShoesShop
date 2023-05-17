package vn.btl.hdvcoreservice.request;

import lombok.Data;

@Data
public class CartRequest {

    private Integer customerId;
    private Integer productId;
    private Integer number;
    private Integer size;
}
