package vn.btl.hdvcoreservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.btl.hdvcoreservice.repository.entity.Order;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {
    List<Order> findByCustomerIdAndCreatedDateBetween(Integer customerId, Date begin, Date end);

    Page<Order> findByCustomerIdAndCreatedDateBetween(Integer customerId, Date begin, Date end, Pageable pageable);


    List<Order> findByCreatedDateBetween(Date begin, Date end);

    Page<Order> findByCreatedDateBetween(Date begin, Date end, Pageable pageable);

}
