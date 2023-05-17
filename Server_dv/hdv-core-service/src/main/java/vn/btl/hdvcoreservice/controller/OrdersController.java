package vn.btl.hdvcoreservice.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.btl.hdvcoreservice.interceptor.Payload;
import vn.btl.hdvcoreservice.request.OrderRequest;
import vn.btl.hdvcoreservice.service.OrdersService;
import vn.btl.hdvcoreservice.service.UserRoleService;
import vn.btl.hdvcoreservice.utils.AppUtils;
import vn.btl.hdvcoreservice.utils.Operator;
import vn.btl.hdvcoreservice.utils.ResponseFactory;

import java.util.List;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrdersController {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/customer")
    public ResponseEntity getOrderCustomer(@RequestAttribute Payload payload, @RequestParam Integer customerId
            , @RequestParam(value = "begin", defaultValue = "1650738769000", required = false) Long begin
            , @RequestParam(value = "end", defaultValue = "1713897169000", required = false) Long end
            , @RequestParam(value = "page", defaultValue = "0", required = false) Integer page
            , @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
            , @RequestParam(value = "direction", defaultValue = "DESC", required = false)String direction
            , @RequestParam(value = "orderBy", defaultValue = "createdDate", required = false)String orderBy){
        Pageable pageable = AppUtils.createPageable(page, pageSize, direction, orderBy);
        log.info("get order customerId {}", customerId);
        return ordersService.getOrderCustomerId(customerId,begin, end , pageable);
    }

    @GetMapping("/get-all")
    public ResponseEntity getAllOrder(@RequestAttribute Payload payload
            , @RequestParam(value = "begin", defaultValue = "1650781900000", required = false) Long begin
            , @RequestParam(value = "end", defaultValue = "1713940300000", required = false) Long end
            , @RequestParam(value = "page", defaultValue = "0", required = false) Integer page
            , @RequestParam(value = "pageSize", defaultValue = "15", required = false) Integer pageSize
            , @RequestParam(value = "direction", defaultValue = "DESC", required = false)String direction
            , @RequestParam(value = "orderBy", defaultValue = "createdDate", required = false)String orderBy){
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.VIEW_ORDER))
            return ResponseFactory.permissionDenied();
        Pageable pageable = AppUtils.createPageable(page, pageSize, direction, orderBy);
        log.info("get all order");
        return ordersService.getOrder(begin, end, pageable);
    }

    @GetMapping("/{orderId;\\d+}")
    public ResponseEntity getOneOrder(@RequestAttribute Payload payload, @PathVariable Integer orderId){
        log.info("get one order {}", orderId);
        return ordersService.getOneOrder(orderId);
    }

    @PostMapping
    public ResponseEntity addOrderCustomer(@RequestAttribute Payload payload, @RequestBody OrderRequest request){
        log.info("create order customer {}", payload.getCustomerId());
        return ordersService.createOrder(request, Math.toIntExact(payload.getCustomerId()));
    }

    @PutMapping("/{orderId:\\d+}/assign")
    public ResponseEntity updateOrder(@RequestAttribute Payload payload, @PathVariable Integer orderId, @RequestParam Integer shipperId){
        log.info("assign order {} to shipper {} by {}", orderId, shipperId, payload.getCustomerId());
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.UPDATE_ORDER_STATUS))
            return ResponseFactory.permissionDenied();
        return ordersService.adminConfirmShipper(orderId,shipperId, Math.toIntExact(payload.getCustomerId()));
    }

    @GetMapping("/{orderId:\\d+}/detail")
    public ResponseEntity getOrderDetail(@RequestAttribute Payload payload, @PathVariable Integer orderId){
        log.info("get order detail {}", orderId);
        return ordersService.getOrderDetailResponse(orderId);
    }

}
