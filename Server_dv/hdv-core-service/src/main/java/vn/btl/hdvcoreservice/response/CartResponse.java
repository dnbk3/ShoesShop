package vn.btl.hdvcoreservice.response;

import lombok.Data;
import java.util.Date;

@Data
public class CartResponse {

    private Integer cartId;
    private Integer customerId;
    private Date createTime;
    private Date updateTime;
    private Integer productId;
    private String productName;
    private String productImage;
    private Integer number;
    private Integer size;
    private Long total;
}
