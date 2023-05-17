package vn.btl.hdvcoreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.btl.hdvcoreservice.repository.entity.OrderDetail;
import vn.btl.hdvcoreservice.response.TopProduct;

import java.util.List;

@Repository
public interface OrderDetailDao extends JpaRepository<OrderDetail, Integer>{

    List<OrderDetail> findByOrderId(Integer orderId);

    @Query(value = "select product_id as productId, sum(quantity) as total from order_detail od group by product_id order by total limit ?1", nativeQuery = true)
    List<TopProduct> findTopProduct(Integer limit);
}
