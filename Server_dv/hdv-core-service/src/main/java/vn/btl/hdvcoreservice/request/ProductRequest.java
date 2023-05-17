package vn.btl.hdvcoreservice.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductRequest {

    private String name;
    private String description;
    private List<String> imageUrls;
    private int size;
    private long discount;
    private int quantity;
    private long price;
    private int supplierId;
    private int categoryId;
    private Integer brandId;
	
    
}
