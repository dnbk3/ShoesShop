package vn.btl.hdvcoreservice.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.btl.hdvcoreservice.interceptor.Payload;
import vn.btl.hdvcoreservice.request.ShipperRequest;
import vn.btl.hdvcoreservice.service.ProductService;
import vn.btl.hdvcoreservice.service.ShipperService;
import vn.btl.hdvcoreservice.service.UserRoleService;
import vn.btl.hdvcoreservice.utils.Operator;
import vn.btl.hdvcoreservice.utils.ResponseFactory;

import java.util.List;

@RestController
@RequestMapping("/shipper")
@Log4j2
public class ShipperController {

    @Autowired
    private ShipperService shipperService;
    @Autowired
    private UserRoleService userRoleService;

    @GetMapping
    public ResponseEntity getAllShipper(@RequestAttribute Payload payload, @RequestParam(value = "keyword",required = false, defaultValue = "keyword") String keyword){
        log.info("get all shipper with keyword {}", keyword);
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.VIEW_SHIPPER))
            return ResponseFactory.permissionDenied();
        return shipperService.getAllShipper(keyword);
    }

    @GetMapping("/{shipperId:\\d+}")
    public ResponseEntity getShipper(@RequestAttribute Payload payload, @PathVariable Integer shipperId ){
        log.info("get one shipper {}", shipperId);
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.VIEW_SHIPPER))
            return ResponseFactory.permissionDenied();
        return shipperService.getOneShipper(shipperId);
    }

    @PostMapping
    public ResponseEntity addShipper(@RequestAttribute Payload payload, @RequestBody ShipperRequest request){
        log.info("add new shipper");
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.CREATE_SHIPPER))
            return ResponseFactory.permissionDenied();
        return shipperService.addShipper(request);
    }

    @PutMapping("/{shipperId:\\d+}")
    public ResponseEntity updateShipper(@RequestAttribute Payload payload, @RequestBody ShipperRequest request, @PathVariable Integer shipperId){
        log.info("update shipper {}", shipperId);
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.UPDATE_SHIPPER))
            return ResponseFactory.permissionDenied();
        return shipperService.updateShipper(request, shipperId);
    }

    @DeleteMapping ("/{shipperId:\\d+}")
    public ResponseEntity deleteShipper(@RequestAttribute Payload payload, @RequestBody ShipperRequest request, @PathVariable Integer shipperId){
        log.info("delete shipper {}", shipperId);
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.DELETE_SHIPPER))
            return ResponseFactory.permissionDenied();
        return shipperService.deleteShipper(shipperId);
    }
}
