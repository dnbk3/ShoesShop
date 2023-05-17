package com.example.demo.Service;

import com.example.demo.Controller.Output.ProductDetailOutput;
import com.example.demo.Model.Category;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    private RestTemplate restTemplate;

    public List<ProductDetailOutput> GetAllProduct(){

        HttpSession session = httpServletRequest.getSession();
        String token = (String) session.getAttribute("token");
        //String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwidXNlcl9pZCI6IjEiLCJwaG9uZSI6IjAxMjM0NTY3OCIsImV4cCI6MTY4NTk1NTQwNSwiaWF0IjoxNjgzMzYzNDA1LCJlbWFpbCI6ImRhb3Ryb25ncGh1YzUwMjA0MDAyQGdtYWlsLmNvbSJ9.Ea9HpCVqRM_bjGEC05xPkPMh6SX7A2-Y_XsFU-TuKm4x5sASkQGHeDTJLB59I5s6m9uUEmYrr-HBWyJLxoMNJg";
        if (token == null) {
            System.out.println("chua login");
            return null;
        }
        String endPoint = "http://localhost:8082/core/product/get-all";
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
                List<ProductDetailOutput> list = objectMapper.readValue(jsonObject
                        .getJSONArray("data").toString(), new TypeReference<List<ProductDetailOutput>>() {});
                System.out.println(list);
                return list;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public List<ProductDetailOutput> GetAllProductByCategoryId(Integer cateId){

        HttpSession session = httpServletRequest.getSession();
        String token = (String) session.getAttribute("token");
        //String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwidXNlcl9pZCI6IjEiLCJwaG9uZSI6IjAxMjM0NTY3OCIsImV4cCI6MTY4NTk1NTQwNSwiaWF0IjoxNjgzMzYzNDA1LCJlbWFpbCI6ImRhb3Ryb25ncGh1YzUwMjA0MDAyQGdtYWlsLmNvbSJ9.Ea9HpCVqRM_bjGEC05xPkPMh6SX7A2-Y_XsFU-TuKm4x5sASkQGHeDTJLB59I5s6m9uUEmYrr-HBWyJLxoMNJg";
        if (token == null) {
            System.out.println("chua login");
            return null;
        }
        String endPoint = "http://localhost:8082/core/product/get-by-categoryId?categoryId=" + cateId;
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
                List<ProductDetailOutput> list = objectMapper.readValue(jsonObject
                        .getJSONArray("data").toString(), new TypeReference<List<ProductDetailOutput>>() {});
                System.out.println(list);
                return list;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public Integer deleteById(Integer id){
        HttpSession session = httpServletRequest.getSession();
        String token = (String) session.getAttribute("token");
        //String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwidXNlcl9pZCI6IjEiLCJwaG9uZSI6IjAxMjM0NTY3OCIsImV4cCI6MTY4NTk1NTQwNSwiaWF0IjoxNjgzMzYzNDA1LCJlbWFpbCI6ImRhb3Ryb25ncGh1YzUwMjA0MDAyQGdtYWlsLmNvbSJ9.Ea9HpCVqRM_bjGEC05xPkPMh6SX7A2-Y_XsFU-TuKm4x5sASkQGHeDTJLB59I5s6m9uUEmYrr-HBWyJLxoMNJg";
        if (token == null) {
            System.out.println("chua login");
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();

        String endPoint = "http://localhost:8082/core/product/"+id+ "/delete";
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setContentType(MediaType.APPLICATION_JSON);
        requestHeader.set("Authorization",token);
        HttpEntity<Integer> httpEntity = new HttpEntity<>(null, requestHeader);
        ResponseEntity response;
        try {
            response = restTemplate.exchange(
                    endPoint,
                    HttpMethod.DELETE,
                    httpEntity,
                    Object.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return 1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

}
