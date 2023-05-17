package vn.btl.hdvcoreservice.service;


import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import vn.btl.hdvcoreservice.request.OrderRequest;

public interface OrdersService {

    ResponseEntity createOrder(OrderRequest request, Integer customerId);

    ResponseEntity adminConfirmShipper(Integer orderId, Integer shipperId, Integer approvedBy);

    ResponseEntity getOrder(Long begin, Long end, Pageable pageable);

    ResponseEntity getOrderCustomerId(Integer customerId, Long begin, Long end, Pageable pageable);

    ResponseEntity getOneOrder(Integer orderId);

    ResponseEntity getOrderDetailResponse(Integer orderId);
}
