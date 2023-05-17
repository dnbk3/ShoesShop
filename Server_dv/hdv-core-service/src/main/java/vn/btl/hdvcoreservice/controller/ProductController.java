package vn.btl.hdvcoreservice.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.btl.hdvcoreservice.interceptor.Payload;
import vn.btl.hdvcoreservice.repository.entity.Brand;
import vn.btl.hdvcoreservice.repository.entity.UserRole;
import vn.btl.hdvcoreservice.request.ProductRequest;
import vn.btl.hdvcoreservice.service.ProductService;
import vn.btl.hdvcoreservice.service.UserRoleService;
import vn.btl.hdvcoreservice.utils.Operator;
import vn.btl.hdvcoreservice.utils.ResponseFactory;

import java.util.List;

@RestController
@RequestMapping("/product")
@Log4j2
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/get-all")
    public ResponseEntity getProducts(
            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword
            , @RequestParam(value = "page", defaultValue = "0", required = false) Integer page
            , @RequestParam(value = "pageSize", defaultValue = "20", required = false) Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return productService.getAllProduct(keyword, pageable);
    }

    @GetMapping("/get-by-supplier")
    public ResponseEntity getProductsBySupplier(@RequestAttribute Payload payload
            , @RequestParam(value = "supplierId") Integer supplierId
            , @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword
            , @RequestParam(value = "page", defaultValue = "0", required = false) Integer page
            , @RequestParam(value = "pageSize", defaultValue = "20", required = false) Integer pageSize) {
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.VIEW_PRODUCT))
            return ResponseFactory.permissionDenied();
        Pageable pageable = PageRequest.of(page, pageSize);
        return productService.getAllProductBySupId(keyword, supplierId, pageable);
    }

    @GetMapping("/get-by-categoryId")
    public ResponseEntity getProductsByCategory(
            @RequestParam(value = "categoryId") Integer categoryId
            , @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword
            , @RequestParam(value = "page", defaultValue = "0", required = false) Integer page
            , @RequestParam(value = "pageSize", defaultValue = "20", required = false) Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return productService.getAllProductByCatId(keyword, categoryId, pageable);
    }

    @GetMapping("/{productId:\\d+}")
    public ResponseEntity getOneProduct(@PathVariable Integer productId) {
        return productService.getOneProduct(productId);
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestAttribute Payload payload, @RequestBody ProductRequest request) {
        log.info("add new product");
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.CREATE_PRODUCT))
            return ResponseFactory.permissionDenied();
        return productService.addProduct(request);
    }

    @PutMapping("/{productId:\\d+}/update")
    public ResponseEntity updateProduct(@RequestAttribute Payload payload, @RequestBody ProductRequest request
            , @PathVariable Integer productId) {
        log.info("update product with id {}", productId);
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.UPDATE_PRODUCT))
            return ResponseFactory.permissionDenied();
        return productService.updateProduct(request, productId);
    }

    @DeleteMapping("/{productId:\\d+}/delete")
    public ResponseEntity deleteProduct(@RequestAttribute Payload payload, @PathVariable Integer productId) {
        log.info("delete product with id {}", productId);
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.DELETE_PRODUCT))
            return ResponseFactory.permissionDenied();
        return productService.deleteProduct(productId);
    }

    @GetMapping("/top")
    public ResponseEntity getTopProduct(
             @RequestParam(value = "limit", defaultValue = "10", required = false)Integer limit){
        log.info("get top product with limit {}", limit);
        return productService.getTopProduct(limit);
    }

    @GetMapping("/{productId:\\d+}/related")
    public ResponseEntity getProductRelated(@PathVariable Integer productId){
        log.info("get product related {}", productId);
        return productService.getRelatedProducts(productId);
    }

    @GetMapping("/search")
    public ResponseEntity searchProduct(
            @RequestParam(value = "categoryId", required = true) Integer categoryId
            , @RequestParam(value = "brandId", required = false) Integer brandId
            , @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword
            , @RequestParam(value = "begin", defaultValue = "-1", required = false) Long beginMoney
            , @RequestParam(value = "end",defaultValue = "9999999999999", required = false)Long endMoney
            , @RequestParam(value = "page", defaultValue = "0", required = false) Integer page
            , @RequestParam(value = "pageSize", defaultValue = "20", required = false) Integer pageSize){
        log.info("search product money from {} to {} with category {} and brand {}", beginMoney, endMoney, categoryId, brandId);
        Pageable pageable = PageRequest.of(page, pageSize);
        return productService.getProductByTypeAndBrandAndPrice(categoryId,keyword,brandId, beginMoney, endMoney, pageable);
    }

    @GetMapping("/get-brand")
    public ResponseEntity getAllBrand(@RequestAttribute Payload payload){
        log.info("get all brand ");
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.VIEW_PRODUCT))
            return ResponseFactory.permissionDenied();
        List<Brand> brandList = productService.getAllBrand();
        return ResponseFactory.success(brandList, List.class);
    }

}
