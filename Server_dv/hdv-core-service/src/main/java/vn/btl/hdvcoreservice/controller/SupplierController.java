package vn.btl.hdvcoreservice.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.btl.hdvcoreservice.interceptor.Payload;
import vn.btl.hdvcoreservice.request.SupplierRequest;
import vn.btl.hdvcoreservice.service.SupplierService;
import vn.btl.hdvcoreservice.service.UserRoleService;
import vn.btl.hdvcoreservice.utils.Operator;
import vn.btl.hdvcoreservice.utils.ResponseFactory;

import java.util.List;

@RestController
@RequestMapping("/supplier")
@Log4j2
public class SupplierController {
    
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private UserRoleService userRoleService;

    @GetMapping
    public ResponseEntity getSuppliers(
            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword){
        log.info("get suppliers with keyword {}", keyword);
        return supplierService.getAllSupplier(keyword);
    }

    @GetMapping("/{supplierId:\\d+}")
    public ResponseEntity getOneSupplier(@RequestAttribute Payload payload, @PathVariable Integer supplierId){
        log.info("get supplier with id {}", supplierId);
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.VIEW_SUPPLIER))
            return ResponseFactory.permissionDenied();
        return supplierService.getOneSuppler(supplierId);
    }

    @PostMapping()
    public ResponseEntity addSupplier(@RequestAttribute Payload payload, @RequestBody SupplierRequest request){
        log.info("add new supplier");
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.CREATE_SUPPLIER))
            return ResponseFactory.permissionDenied();
        return supplierService.addSupplier(request);
    }

    @PutMapping("/{supplierId:\\d+}")
    public ResponseEntity updateSupplier(@RequestAttribute Payload payload, @RequestBody SupplierRequest request
            , @PathVariable Integer supplierId){
        log.info("update supplier with id {}", supplierId);
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.UPDATE_SUPPLIER))
            return ResponseFactory.permissionDenied();
        return supplierService.updateSupplier(request,supplierId);
    }

    @DeleteMapping("/{supplierId:\\d+}")
    public ResponseEntity deleteSupplier(@RequestAttribute Payload payload, @PathVariable Integer supplierId){
        log.info("delete supplier with id {}", supplierId);
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.DELETE_SUPPLIER))
            return ResponseFactory.permissionDenied();
        return supplierService.deleteSupplier(supplierId);
    }

}
