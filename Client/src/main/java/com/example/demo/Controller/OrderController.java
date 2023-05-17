package com.example.demo.Controller;

import com.example.demo.Model.Orders;
import com.example.demo.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @GetMapping("/order/confirm")
    public String confirmOrder(@RequestParam(value = "id",required = true) Long orderId) {
        Orders orders = orderService.confirm(orderId);
        if(orders==null){
            return "redirect:eror1";
        }else{
            return "redirect:/viewOrderStaff";
        }

    }
}
