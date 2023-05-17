package vn.btl.hdvcoreservice.service;

import org.springframework.http.ResponseEntity;
import vn.btl.hdvcoreservice.service.VNPay.VnPayRequest;

public interface VnPayService {
    ResponseEntity payOrder(VnPayRequest request) throws Exception;
}
