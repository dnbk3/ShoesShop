package vn.btl.hdvcoreservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.btl.hdvcoreservice.exception.CoreErrorCode;
import vn.btl.hdvcoreservice.exception.CoreException;
import vn.btl.hdvcoreservice.repository.CartDao;
import vn.btl.hdvcoreservice.repository.ProductDao;
import vn.btl.hdvcoreservice.repository.entity.Cart;
import vn.btl.hdvcoreservice.repository.entity.Product;
import vn.btl.hdvcoreservice.request.CartRequest;
import vn.btl.hdvcoreservice.response.CartResponse;
import vn.btl.hdvcoreservice.service.CartService;
import vn.btl.hdvcoreservice.utils.AppUtils;
import vn.btl.hdvcoreservice.utils.ResponseFactory;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public ResponseEntity saveCart(CartRequest request){
        Cart cart = cartDao.findByCustomerIdAndProductId(request.getCustomerId(), request.getProductId()).orElse(new Cart());
        if(cart.getCartId() == null){
            cart.setCreateTime(new Date());
            cart.setUpdateTime(new Date());
            AppUtils.copyPropertiesIgnoreNull(request,cart);
            Product product = productDao.findOneByProductId(request.getProductId());
            if(product == null)
                throw new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS);
            String [] pictures = product.getPictures().split(",");
            if(pictures.length>0)
                cart.setProductImage(pictures[0]);
        }
        else {
            cart.setUpdateTime(new Date());
            cart.setNumber(request.getNumber());
            cart.setSize(request.getSize());
        }
        return ResponseFactory.success(cartDao.save(cart), Cart.class);
    }

    @Override
    public ResponseEntity getCartCustomer(Integer customerId){
        List<Cart> carts = cartDao.findByCustomerId(customerId);
        List<CartResponse> responses = carts.stream().map(o -> cartResponse(o)).collect(Collectors.toList());
        return ResponseFactory.success(responses, List.class);
    }

    @Override
    public ResponseEntity deleteCartCustomer(Integer cartId){
        Cart cart = cartDao.findById(cartId).orElseThrow
                (() -> new CoreException(CoreErrorCode.ENTITY_EXISTED));
        cartDao.delete(cart);
        return ResponseFactory.success();
    }

    @Override
    public ResponseEntity getOneCart(Integer cartId) {
        Cart cart = cartDao.findById(cartId).orElseThrow(() -> new CoreException
                (CoreErrorCode.ENTITY_NOT_EXISTS, "Không tìm thấy sản phẩm trong giỏ hàng"));
        CartResponse cartResponse = cartResponse(cart);
        return ResponseFactory.success(cartResponse, CartResponse.class);
    }

    public CartResponse cartResponse(Cart cart){
        CartResponse cartResponse = new CartResponse();
        AppUtils.copyPropertiesIgnoreNull(cart, cartResponse);
        Product product = productDao.findOneByProductId(cart.getProductId());
        if(product == null)
            throw new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS);
        cartResponse.setProductName(product.getName());
        cartResponse.setTotal(cart.getNumber()*product.getDiscount());
        return cartResponse;
    }
}
