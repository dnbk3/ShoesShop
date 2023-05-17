package vn.btl.hdvcoreservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.btl.hdvcoreservice.interceptor.Payload;
import vn.btl.hdvcoreservice.request.CartRequest;
import vn.btl.hdvcoreservice.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    private Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity getCartCustomer(@RequestAttribute Payload payload){
        logger.info("get cart customer {}", payload.getCustomerId());
        return cartService.getCartCustomer(Math.toIntExact(payload.getCustomerId()));
    }

    @GetMapping("/{cartId:\\d+}")
    public ResponseEntity getOneCart(@RequestAttribute Payload payload, @PathVariable Integer cartId){
        logger.info("get one cart id {}", cartId);
        return cartService.getOneCart(cartId);
    }

    @PostMapping
    public ResponseEntity saveCartCustomer(@RequestAttribute Payload payload, @RequestBody CartRequest request){
        logger.info("save cart customer {}",request);
        request.setCustomerId(Math.toIntExact(payload.getCustomerId()));
        return cartService.saveCart(request);
    }

    @DeleteMapping("/{cartId:\\d+}")
    public ResponseEntity deleteCart(@RequestAttribute Payload payload, @PathVariable Integer cartId){
        logger.info("delete cart customer {}", cartId);
        return cartService.deleteCartCustomer(cartId);
    }



}
