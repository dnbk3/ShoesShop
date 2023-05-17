package vn.btl.hdvcoreservice.response;

import lombok.Data;

@Data
public class ProductResponse {

    private int productId;
    private String name;
    private String description;
    private String pictures;
    private String image;
    private int size;
    private int discount;
    private int quantity;
    private long price;
    private int supplierId;
    private String supplier;
    private int categoryId;
    private String category;
    private Integer brandId;
    private String brandName;
}
