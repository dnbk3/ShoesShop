package vn.btl.hdvcoreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.btl.hdvcoreservice.repository.entity.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {

}
