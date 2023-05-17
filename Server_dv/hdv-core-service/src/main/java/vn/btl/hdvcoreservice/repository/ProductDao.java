package vn.btl.hdvcoreservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.btl.hdvcoreservice.repository.entity.Product;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

    Product findOneByProductId(Integer productId);

    Page<Product> findByNameContainsIgnoreCase(String keyword, Pageable pageable);

    List<Product> findByNameContainsIgnoreCase(String keyword);


    Page<Product> findByNameContainsIgnoreCaseAndCategoryId(String keyword, Integer catId, Pageable pageable);

    List<Product> findByNameContainsIgnoreCaseAndCategoryId(String keyword, Integer catId);

    List<Product> findByNameContainsIgnoreCaseAndSupplierId(String keyword, Integer catId);

    Page<Product> findByNameContainsIgnoreCaseAndSupplierId(String keyword, Integer catId, Pageable pageable);

    Page<Product> findByNameContainsIgnoreCaseAndCategoryIdAndPriceBetweenAndBrandId(String brand, Integer categoryId, Long begin, Long end, Integer brandId, Pageable pageable);
    Page<Product> findByNameContainsIgnoreCaseAndCategoryIdAndPriceBetween(String brand, Integer categoryId, Long begin, Long end, Pageable pageable);

    Page<Product> findByProductIdNotIn(Set<Integer> productIds, Pageable pageable);

}
