package com.example.demo.Service;

import com.example.demo.Controller.Output.OrderResponseWithDetails;
import com.example.demo.Model.Brand;
import com.example.demo.Model.Orders;
import com.example.demo.Model.OrdersDetail;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    private RestTemplate restTemplate;

    public List<Orders> GetAll(){

        HttpSession session = httpServletRequest.getSession();
        String token = (String) session.getAttribute("token");
        //String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwidXNlcl9pZCI6IjEiLCJwaG9uZSI6IjAxMjM0NTY3OCIsImV4cCI6MTY4NTk1NTQwNSwiaWF0IjoxNjgzMzYzNDA1LCJlbWFpbCI6ImRhb3Ryb25ncGh1YzUwMjA0MDAyQGdtYWlsLmNvbSJ9.Ea9HpCVqRM_bjGEC05xPkPMh6SX7A2-Y_XsFU-TuKm4x5sASkQGHeDTJLB59I5s6m9uUEmYrr-HBWyJLxoMNJg";
        if (token == null) {
            System.out.println("chua login");
            return null;
        }
        String endPoint = "http://localhost:8082/core/order/get-all";
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setContentType(MediaType.APPLICATION_JSON);
        requestHeader.set("Authorization",token);

        HttpEntity<Object> httpEntity = new HttpEntity<>(null, requestHeader);
        ResponseEntity response;
        try {
            response = restTemplate.exchange(
                    endPoint,
                    HttpMethod.GET,
                    httpEntity,
                    Object.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String body = objectMapper.writeValueAsString(response.getBody());
                JSONObject jsonObject = new JSONObject(body);
                List<Orders> list = objectMapper.readValue(jsonObject
                        .getJSONArray("data").toString(), new TypeReference<List<Orders>>() {});
                System.out.println(list);
                return list;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }
    public Orders confirm(Long orderId){
        HttpSession session = httpServletRequest.getSession();
        String token = (String) session.getAttribute("token");
        if (token == null ) {
            System.out.println("chua login");
            return null;
        }

        if (orderId == null) {
            System.out.println("error id");
            return null;
        }
        String endPoint = "http://localhost:8082/core/order/"+ orderId+"/assign?shipperId=1";
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setContentType(MediaType.APPLICATION_JSON);
        requestHeader.set("Authorization",token);
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, requestHeader);
        //System.out.println(object);
        ResponseEntity response;
        try {
            response = restTemplate.exchange(
                    endPoint,
                    HttpMethod.PUT,
                    httpEntity,
                    Object.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String body = objectMapper.writeValueAsString(response.getBody());
                JSONObject jsonObject = new JSONObject(body);
                Orders res = objectMapper.readValue(jsonObject
                        .getJSONObject("data").toString(), new TypeReference<Orders>() {});
                System.out.println(res);
                return res;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public OrderResponseWithDetails getAllOrdersDetail(Long orderId) {
        HttpSession session = httpServletRequest.getSession();
        String token = (String) session.getAttribute("token");
        //String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwidXNlcl9pZCI6IjEiLCJwaG9uZSI6IjAxMjM0NTY3OCIsImV4cCI6MTY4NTk1NTQwNSwiaWF0IjoxNjgzMzYzNDA1LCJlbWFpbCI6ImRhb3Ryb25ncGh1YzUwMjA0MDAyQGdtYWlsLmNvbSJ9.Ea9HpCVqRM_bjGEC05xPkPMh6SX7A2-Y_XsFU-TuKm4x5sASkQGHeDTJLB59I5s6m9uUEmYrr-HBWyJLxoMNJg";
        if (token == null) {
            System.out.println("chua login");
            return null;
        }
        // Thực hiện HTTP GET request tới API
        String endpoint = "http://localhost:8082/core/order/"+orderId+"/detail";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.GET, entity, String.class);

        // Chuyển đổi JSON response thành đối tượng OrderResponseWithDetails
        ObjectMapper mapper = new ObjectMapper();
        try {
            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONObject dataObject = jsonObject.getJSONObject("data");
            Orders orderResponse = mapper.readValue(dataObject.getJSONObject("orderResponse").toString(), Orders.class);
            List<OrdersDetail> orderDetails = mapper.readValue(dataObject.getJSONArray("orderDetails").toString(), new TypeReference<List<OrdersDetail>>() {});
            return new OrderResponseWithDetails(orderResponse, orderDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
