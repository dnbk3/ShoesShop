package com.example.demo.Controller.Output;

import com.example.demo.Model.Orders;
import com.example.demo.Model.OrdersDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseWithDetails {
    private Orders orderResponse;
    private List<OrdersDetail> orderDetails;
}
