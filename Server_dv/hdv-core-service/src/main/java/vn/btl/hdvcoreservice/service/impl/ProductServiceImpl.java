package vn.btl.hdvcoreservice.service.impl;


import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.btl.hdvcoreservice.exception.CoreErrorCode;
import vn.btl.hdvcoreservice.exception.CoreException;
import vn.btl.hdvcoreservice.repository.*;
import vn.btl.hdvcoreservice.repository.entity.Brand;
import vn.btl.hdvcoreservice.repository.entity.Category;
import vn.btl.hdvcoreservice.repository.entity.Product;
import vn.btl.hdvcoreservice.repository.entity.Supplier;
import vn.btl.hdvcoreservice.request.ProductRequest;
import vn.btl.hdvcoreservice.response.ProductResponse;
import vn.btl.hdvcoreservice.response.TopProduct;
import vn.btl.hdvcoreservice.response.TopProductResponse;
import vn.btl.hdvcoreservice.service.ImageService;
import vn.btl.hdvcoreservice.service.ProductService;
import vn.btl.hdvcoreservice.utils.AppUtils;
import vn.btl.hdvcoreservice.utils.PageResponse;
import vn.btl.hdvcoreservice.utils.PageResponseUtil;
import vn.btl.hdvcoreservice.utils.ResponseFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductDao productDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private SupplierDao supplierDao;
    @Autowired
    private ImageService imageService;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private BrandDao brandDao;

    @Override
    public ResponseEntity addProduct(ProductRequest request) {
    	log.info(request);
        Product product = new Product();
        AppUtils.copyPropertiesIgnoreNull(request, product);
        String pictures = request.getImageUrls().stream().collect(Collectors.joining(","));
        product.setPictures(pictures);
        log.info("add new product");
        return ResponseFactory.success(productDao.save(product), Product.class);
    }

    @Override
    public ResponseEntity updateProduct(ProductRequest request, Integer productId) {
        Product product = productDao.findOneByProductId(productId);
        if (product == null)
            throw new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS);
        AppUtils.copyPropertiesIgnoreNull(request, product);
        if (request.getImageUrls().size() > 0) {
            String pictures = request.getImageUrls().stream().collect(Collectors.joining(","));
            product.setPictures(pictures);
        }
        log.info("update product {}", productId);
        return ResponseFactory.success(productDao.save(product), Product.class);
    }

    @Override
    public ResponseEntity getAllProduct(String keyword, Pageable pageable) {
        Page<Product> products = productDao.findByNameContainsIgnoreCase(keyword, pageable);
        List<ProductResponse> responses = products.stream().map(o -> {
            return productResponse(o);
        }).collect(Collectors.toList());
        PageResponse pageResponse = PageResponseUtil.buildPageResponse(products);
        log.info("get all product with keyword {}", keyword);
        return ResponseFactory.success(responses, pageResponse);
    }

    @Override
    public ResponseEntity getOneProduct(Integer productId) {
        Product product = productDao.findOneByProductId(productId);
        if (product == null)
            throw new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS);
        return ResponseFactory.success(product, Product.class);
    }


    @Override
    public ResponseEntity getAllProductByCatId(String keyword, Integer catId, Pageable pageable) {
        Page<Product> products = productDao.findByNameContainsIgnoreCaseAndCategoryId(keyword, catId, pageable);
        List<ProductResponse> responses = products.stream().map(o -> {
            return productResponse(o);
        }).collect(Collectors.toList());
        PageResponse pageResponse = PageResponseUtil.buildPageResponse(products);
        log.info("get product with catId {}", catId);
        return ResponseFactory.success(responses, pageResponse);
    }


    @Override
    public ResponseEntity getAllProductBySupId(String keyword, Integer supId, Pageable pageable) {
        Page<Product> products = productDao.findByNameContainsIgnoreCaseAndSupplierId(keyword, supId, pageable);
        List<ProductResponse> responses = products.stream().map(o -> {
            return productResponse(o);
        }).collect(Collectors.toList());
        PageResponse pageResponse = PageResponseUtil.buildPageResponse(products);
        log.info("get product with supplierId {}", supId);
        return ResponseFactory.success(responses, pageResponse);
    }

    @Override
    public ResponseEntity deleteProduct(Integer productId) {
        Product product = productDao.findOneByProductId(productId);
        if (product == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        productDao.delete(product);
        return ResponseFactory.success();
    }


    @Override
    public ProductResponse productResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        AppUtils.copyPropertiesIgnoreNull(product, productResponse);
        Supplier supplier = supplierDao.findById(product.getSupplierId()).orElse(new Supplier());
        String[] w = product.getPictures().split(",");
        productResponse.setImage(w[0]);
        productResponse.setSupplier(supplier.getName());
        Category category = categoryDao.findById(product.getCategoryId()).orElse(new Category());
        productResponse.setCategory(category.getName());
        Brand brand = brandDao.findById(product.getBrandId()).orElse(new Brand());
        productResponse.setBrandName(brand.getName());
        return productResponse;
    }

    @Override
    public ResponseEntity getTopProduct(Integer limit) {
        List<TopProduct> topProducts = orderDetailDao.findTopProduct(limit);
        if (topProducts.size() < limit) {
            Set<Integer> productIds = topProducts.stream().map(o -> o.getProductId()).collect(Collectors.toSet());
            if (productIds.size() == 0)
                productIds.add(-1);
            Pageable pageable = PageRequest.of(0, limit - topProducts.size());
            List<Product> products = productDao.findByProductIdNotIn(productIds, pageable).getContent();
            logger.info("top product " + topProducts.size());
            List<TopProduct> topProducts1 = products.stream().map(o -> {
                TopProduct topProduct = new TopProduct() {
                    @Override
                    public Integer getProductId() {
                        return o.getProductId();
                    }

                    @Override
                    public Integer getTotal() {
                        return 0;
                    }
                };
                return topProduct;
            }).collect(Collectors.toList());
            topProducts.addAll(topProducts1);
        }
        List<TopProductResponse> responses = new ArrayList<>();
        for (TopProduct topProduct : topProducts) {
            Product product = productDao.findOneByProductId(topProduct.getProductId());
            if (product != null) {
                TopProductResponse response = new TopProductResponse();
                AppUtils.copyPropertiesIgnoreNull(product, response);
                String[] w = product.getPictures().split(",");
                response.setImage(w[0]);
                responses.add(response);
            }
        }
        return ResponseFactory.success(responses, List.class);
    }

    @Override
    public ResponseEntity getProductByTypeAndBrandAndPrice(Integer categoryId, String keyword, Integer brandId, Long begin, Long end, Pageable pageable) {
        Page<Product> products = null;
        if(brandId != null){
            products = productDao.findByNameContainsIgnoreCaseAndCategoryIdAndPriceBetweenAndBrandId(keyword, categoryId, begin, end, brandId, pageable);
        }
        else
            products = productDao.findByNameContainsIgnoreCaseAndCategoryIdAndPriceBetween(keyword, categoryId, begin, end, pageable);
        List<ProductResponse> responses = products.stream().map(o -> {
            return productResponse(o);
        }).collect(Collectors.toList());
        PageResponse pageResponse = PageResponseUtil.buildPageResponse(products);
        return ResponseFactory.success(responses, pageResponse);
    }

    @Override
    public ResponseEntity getRelatedProducts(Integer productId) {
        Product product = productDao.findById(productId).orElseThrow(() -> new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS));
        List<Product> products = productDao.findByNameContainsIgnoreCaseAndCategoryId("", product.getCategoryId());
        List<ProductResponse> response = new ArrayList<>();
        for (Product p : products) {
            if (p.getProductId() != productId)
                response.add(productResponse(p));
            if (response.size() > 4)
                break;
        }
        return ResponseFactory.success(response, List.class);
    }

    @Override
    public List<Brand> getAllBrand() {
        List<Brand> brands = brandDao.findAll();
        return brands;
    }

}
