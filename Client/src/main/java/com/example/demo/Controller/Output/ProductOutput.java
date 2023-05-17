package com.example.demo.Controller.Output;

public class ProductOutput {
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

    public ProductOutput() {
    }

    public ProductOutput(int productId, String name, String description, String pictures, String image, int size, int discount, int quantity, long price, int supplierId, String supplier, int categoryId, String category, Integer brandId, String brandName) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.pictures = pictures;
        this.image = image;
        this.size = size;
        this.discount = discount;
        this.quantity = quantity;
        this.price = price;
        this.supplierId = supplierId;
        this.supplier = supplier;
        this.categoryId = categoryId;
        this.category = category;
        this.brandId = brandId;
        this.brandName = brandName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
