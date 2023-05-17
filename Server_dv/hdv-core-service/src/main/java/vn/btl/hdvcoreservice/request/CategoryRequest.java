package vn.btl.hdvcoreservice.request;

import lombok.Data;

@Data
public class CategoryRequest {

    private String name;
    private String description;
    private String picture;
    private boolean active;
}
