package vn.btl.hdvcoreservice.response;

import lombok.Data;
import java.util.Date;

@Data
public class OrderResponse {

    private int orderId;
    private long totalAmount;
    private Date createdDate;
    private String paymentMethod;
    private int status;
    private long shippingPrice;
    private String note;
    private String address;
    private String orderCode;
    private String phone;
    private int shipperId;
    private String shipperName;
    private String customerName;
    private String customerPhone;
    private int customerId;
    private Integer approveBy;
    private String approvedName;
    private String approvedPhone;
}
