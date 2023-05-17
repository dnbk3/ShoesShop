package vn.btl.hdvcoreservice.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.btl.hdvcoreservice.exception.CoreErrorCode;
import vn.btl.hdvcoreservice.exception.CoreException;
import vn.btl.hdvcoreservice.repository.*;
import vn.btl.hdvcoreservice.repository.entity.*;
import vn.btl.hdvcoreservice.request.OrderRequest;
import vn.btl.hdvcoreservice.response.DetailOrder;
import vn.btl.hdvcoreservice.response.OrderDetailResponse;
import vn.btl.hdvcoreservice.response.OrderResponse;
import vn.btl.hdvcoreservice.service.OrdersService;
import vn.btl.hdvcoreservice.utils.AppUtils;
import vn.btl.hdvcoreservice.utils.PageResponse;
import vn.btl.hdvcoreservice.utils.PageResponseUtil;
import vn.btl.hdvcoreservice.utils.ResponseFactory;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrdersService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private SystemCustomerDao customerDao;

    @Override
    public ResponseEntity createOrder(OrderRequest request, Integer customerId){
        Order order = new Order();
        String orderCode = RandomStringUtils.random(8, true, true).toUpperCase();
        order.setOrderCode(orderCode);
        order.setCreatedDate(new Date());
        order.setCustomerId(customerId);
        order.setAddress(request.getAddress());
        order.setNote(request.getNote());
        order.setPhone(request.getPhone());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setStatus(0);
        List<Product> products = request.getRequests().stream().map(o -> {
            Product product = productDao.findById(o.getProductId()).orElseThrow(() -> new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS));
            return product;
        }).collect(Collectors.toList());
        long totalAmount = products.stream().mapToLong(o -> o.getDiscount()).sum();
        long shippingPrice = Math.round(totalAmount* 1.0f /100);
        order.setShippingPrice(shippingPrice);
        order.setTotalAmount(totalAmount);
        Order order1 = orderDao.save(order);
        request.getRequests().stream().map(o -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order1.getOrderId());
            Product product = productDao.findById(o.getProductId()).orElseThrow(() -> new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS));
            orderDetail.setProductPrice(product.getDiscount());
            orderDetail.setProductName(product.getName());
            orderDetail.setQuantity(o.getNumber());
            orderDetail.setAmount(o.getNumber()*product.getDiscount());
            String []w = product.getPictures().split(",");
            orderDetail.setProductImage(w[0]);
            return orderDetailDao.save(orderDetail);
        }).collect(Collectors.toList());
        return ResponseFactory.success(order1, Order.class);
    }

    @Override
    public ResponseEntity adminConfirmShipper(Integer orderId, Integer shipperId, Integer approvedBy){
        Order order = orderDao.findById(orderId).orElseThrow(() -> new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS));
        order.setShipperId(shipperId);
        order.setStatus(1);
        order.setApprovedBy(approvedBy);
        return ResponseFactory.success(orderDao.save(order), Order.class);
    }

    @Override
    public ResponseEntity getOrder(Long begin, Long end, Pageable pageable){
        Page<Order> orders = orderDao.findByCreatedDateBetween(new Date(begin), new Date(end), pageable);
        List<OrderResponse> responses = orders.stream().map(o -> {
           return createOrderResponse(o);
        }).collect(Collectors.toList());
        PageResponse pageResponse = PageResponseUtil.buildPageResponse(orders);
        return ResponseFactory.success(responses, pageResponse);
    }

    @Override
    public ResponseEntity getOrderCustomerId(Integer customerId, Long begin, Long end, Pageable pageable){
        Page<Order> orders = orderDao.findByCustomerIdAndCreatedDateBetween(customerId, new Date(begin), new Date(end), pageable);
        List<OrderResponse> responses = orders.stream().map(o -> {
            return createOrderResponse(o);
        }).collect(Collectors.toList());
        PageResponse pageResponse = PageResponseUtil.buildPageResponse(orders);
        return ResponseFactory.success(responses, pageResponse);
    }

    @Override
    public ResponseEntity getOneOrder(Integer orderId){
        Order order = orderDao.findById(orderId).orElseThrow(() -> new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS));
        OrderResponse response = createOrderResponse(order);
        return ResponseFactory.success(response, OrderResponse.class);
    }

    OrderResponse createOrderResponse(Order o){
        OrderResponse response = new OrderResponse();
        AppUtils.copyPropertiesIgnoreNull(o, response);
        SystemCustomer customer = customerDao.findById(Long.valueOf(o.getCustomerId())).orElseThrow(() -> new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS));
        response.setCustomerName(customer.getName());
        response.setCustomerPhone(customer.getPhone());
        if(o.getShipperId()!= null){
            customer = customerDao.findById(Long.valueOf(o.getShipperId())).orElseThrow(() -> new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS));
            response.setShipperName(customer.getName());
            response.setPhone(customer.getPhone());
        }
        if(o.getApprovedBy()!= null){
            customer = customerDao.findById(Long.valueOf(o.getApprovedBy())).orElseThrow(() -> new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS));
            response.setApprovedName(customer.getName());
            response.setApprovedName(customer.getPhone());
        }
        return response;
    }

    @Override
    public ResponseEntity getOrderDetailResponse(Integer orderId){
        Order order = orderDao.findById(orderId).orElseThrow(() -> new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS));
        OrderResponse orderResponse = createOrderResponse(order);
        List<OrderDetail> orderDetails = orderDetailDao.findByOrderId(orderId);
        List<OrderDetailResponse> responses = orderDetails.stream().map(o ->{
            OrderDetailResponse response = new OrderDetailResponse();
            AppUtils.copyPropertiesIgnoreNull(o, response);
            return response;
        }).collect(Collectors.toList());
        DetailOrder detailOrder = DetailOrder.builder()
                .orderDetails(responses).orderResponse(orderResponse).build();
        return ResponseFactory.success(detailOrder, DetailOrder.class);
    }
}
