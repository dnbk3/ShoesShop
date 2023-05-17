package vn.btl.hdvcoreservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.btl.hdvcoreservice.service.VNPay.VnPayRequest;
import vn.btl.hdvcoreservice.service.VnPayService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/vn-pay")
public class VnPayController {

    @Autowired
    private VnPayService vnPayService;

    @PostMapping
    public ResponseEntity payOrder(@RequestBody VnPayRequest request, HttpServletRequest httpRequest) throws Exception {
        request.setIpAddr(httpRequest.getRemoteAddr());
        return vnPayService.payOrder(request);
    }
}
