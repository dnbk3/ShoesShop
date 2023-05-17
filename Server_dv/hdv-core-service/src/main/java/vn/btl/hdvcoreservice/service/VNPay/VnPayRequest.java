package vn.btl.hdvcoreservice.service.VNPay;

import lombok.Data;

@Data
public class VnPayRequest {

    Integer orderId;
    String orderInfo;
    int amount;
    String bankCode;
    String ipAddr;
}
