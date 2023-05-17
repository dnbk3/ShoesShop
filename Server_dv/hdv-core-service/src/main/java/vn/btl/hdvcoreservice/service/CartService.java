package vn.btl.hdvcoreservice.service;

import org.springframework.http.ResponseEntity;
import vn.btl.hdvcoreservice.request.CartRequest;

public interface CartService {
    ResponseEntity saveCart(CartRequest request);

    ResponseEntity getCartCustomer(Integer customerId);

    ResponseEntity deleteCartCustomer(Integer cartId);

    ResponseEntity getOneCart(Integer cartId);
}
