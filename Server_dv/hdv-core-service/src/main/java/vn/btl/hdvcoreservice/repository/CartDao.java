package vn.btl.hdvcoreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.btl.hdvcoreservice.repository.entity.Cart;

import java.util.List;
import java.util.Optional;

public interface CartDao extends JpaRepository<Cart, Integer>, JpaSpecificationExecutor<Cart> {

    Optional<Cart> findByCustomerIdAndProductId(Integer customerId, Integer productId);

    List<Cart> findByCustomerId(Integer customerId);
}

