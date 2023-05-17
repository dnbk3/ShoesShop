package vn.btl.hdvcoreservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.btl.hdvcoreservice.repository.entity.SystemCustomer;

public interface SystemCustomerDao extends JpaRepository<SystemCustomer, Long>, JpaSpecificationExecutor<SystemCustomer> {

    SystemCustomer findOneByCustomerId(Long customerId);

    SystemCustomer findOneByPhone(String phone);

    SystemCustomer findOneByEmail(String email);

    Page<SystemCustomer> findByPhoneContainsIgnoreCaseOrNameContainsIgnoreCaseOrEmailContainsIgnoreCase(String keyword, String keyword2, String keyword3,Pageable pageable);

}