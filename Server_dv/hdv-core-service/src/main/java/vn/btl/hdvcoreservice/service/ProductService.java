package vn.btl.hdvcoreservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import vn.btl.hdvcoreservice.repository.entity.Brand;
import vn.btl.hdvcoreservice.repository.entity.Product;
import vn.btl.hdvcoreservice.request.ProductRequest;
import vn.btl.hdvcoreservice.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ResponseEntity addProduct(ProductRequest request);

    ResponseEntity updateProduct(ProductRequest request, Integer productId);

    ResponseEntity getAllProduct(String keyword, Pageable pageable);

    ResponseEntity getOneProduct(Integer productId);

    ResponseEntity getAllProductByCatId(String keyword, Integer catId, Pageable pageable);

    ResponseEntity getAllProductBySupId(String keyword, Integer supId, Pageable pageable);

    ResponseEntity deleteProduct(Integer productId);

    ProductResponse productResponse(Product product);

    ResponseEntity getTopProduct(Integer limit);

    ResponseEntity getProductByTypeAndBrandAndPrice(Integer categoryId, String keyword, Integer brandId, Long beginMoney, Long endMoney ,Pageable pageable);

    ResponseEntity getRelatedProducts(Integer productId);

    List<Brand> getAllBrand();

}
