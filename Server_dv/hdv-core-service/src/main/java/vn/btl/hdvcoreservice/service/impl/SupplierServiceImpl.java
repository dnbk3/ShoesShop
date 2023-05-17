package vn.btl.hdvcoreservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.btl.hdvcoreservice.exception.CoreErrorCode;
import vn.btl.hdvcoreservice.exception.CoreException;
import vn.btl.hdvcoreservice.repository.SupplierDao;
import vn.btl.hdvcoreservice.repository.entity.Supplier;
import vn.btl.hdvcoreservice.request.SupplierRequest;
import vn.btl.hdvcoreservice.service.SupplierService;
import vn.btl.hdvcoreservice.utils.AppUtils;
import vn.btl.hdvcoreservice.utils.ResponseFactory;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierDao supplierDao;

    @Override
    public ResponseEntity getAllSupplier(String keyword){
        List<Supplier> suppliers = supplierDao.findByNameContainsIgnoreCase(keyword);
        return ResponseFactory.success(suppliers, List.class);
    }

    @Override
    public ResponseEntity addSupplier(SupplierRequest request){
        Supplier supplier = new Supplier();
        AppUtils.copyPropertiesIgnoreNull(request,supplier);
        return ResponseFactory.success(supplierDao.save(supplier), Supplier.class);
    }

    @Override
    public ResponseEntity updateSupplier(SupplierRequest request, Integer supplierId){
        Supplier supplier = supplierDao.findOneBySupplierId(supplierId);
        if(supplier == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        AppUtils.copyPropertiesIgnoreNull(request, supplier);
        return ResponseFactory.success(supplierDao.save(supplier), Supplier.class);
    }


    @Override
    public ResponseEntity getOneSuppler(Integer supplierId){
        Supplier supplier = supplierDao.findOneBySupplierId(supplierId);
        if(supplier == null)
            throw new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS);
        return ResponseFactory.success(supplier, Supplier.class);
    }

    @Override
    public ResponseEntity deleteSupplier(Integer supplierId){
        Supplier supplier = supplierDao.findOneBySupplierId(supplierId);
        if(supplier == null)
            throw new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS);
        supplierDao.delete(supplier);
        return ResponseFactory.success();
    }
}
