package vn.btl.hdvcoreservice.service;


import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import vn.btl.hdvcoreservice.repository.entity.SystemCustomer;
import vn.btl.hdvcoreservice.request.NewSystemCustomerRequest;
import vn.btl.hdvcoreservice.request.UpdateSystemCustomerRequest;

public interface CustomerService {

    SystemCustomer addCustomer(NewSystemCustomerRequest request);


    SystemCustomer updateCustomer(Long customerId, UpdateSystemCustomerRequest request);

    SystemCustomer getByCustomerId(Long customerId);

    void deleteCustomer(Long customerId);

    ResponseEntity getAllCustomer(String keyword, Pageable pageable);
}
