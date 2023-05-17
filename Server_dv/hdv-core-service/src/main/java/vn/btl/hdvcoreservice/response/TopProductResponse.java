package vn.btl.hdvcoreservice.response;

import lombok.Data;

@Data
public class TopProductResponse {

    Integer productId;
    String name;
    String image;
    Long price;
}
