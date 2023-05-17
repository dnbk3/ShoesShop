package com.example.demo.Controller.Output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailOutput {
    private int productId;
    private String name;
    private String description;
    private String pictures;
    private String image;
    private int size;
    private int discount;
    private int quantity;
    private int price;
    private int supplierId;
    private String supplier;
    private int categoryId;
    private String category;
    private int brandId;
    private String brandName;



}
