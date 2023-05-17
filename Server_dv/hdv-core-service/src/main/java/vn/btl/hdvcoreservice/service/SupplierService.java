package vn.btl.hdvcoreservice.service;


import org.springframework.http.ResponseEntity;
import vn.btl.hdvcoreservice.request.SupplierRequest;

public interface SupplierService {

    ResponseEntity getAllSupplier(String keyword);

    ResponseEntity addSupplier(SupplierRequest request);

    ResponseEntity updateSupplier(SupplierRequest request, Integer supplierId);

    ResponseEntity getOneSuppler(Integer supplierId);

    ResponseEntity deleteSupplier(Integer supplierId);
}
