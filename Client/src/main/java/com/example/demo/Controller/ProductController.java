package com.example.demo.Controller;

import com.example.demo.Controller.Output.ProductDetailOutput;
import com.example.demo.Controller.input.ProductInput;
import com.example.demo.Model.Category;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Product;
import com.example.demo.Model.ReCaptchaResponse;
import com.example.demo.Service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.util.*;

@Controller
public class ProductController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProductService productService;

    @SneakyThrows
    @PostMapping("/admin/newproduct")
    public String newProduct(HttpServletRequest request,@ModelAttribute ProductInput product) {
        HttpSession session = request.getSession();
        String token = (String) session.getAttribute("token");
        if (token == null) {
            System.out.println("chua login");
            return "redirect:/login";
        }
        ObjectMapper mapper = new ObjectMapper();
        String object = mapper.writeValueAsString(product);
        String endPoint = "http://localhost:8082/core/product/add";
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setContentType(MediaType.APPLICATION_JSON);
        requestHeader.set("Authorization",token);
        HttpEntity<Object> httpEntity = new HttpEntity<>(object, requestHeader);
        System.out.println(object);
        ResponseEntity response;
        try {
            response = restTemplate.exchange(
                    endPoint,
                    HttpMethod.POST,
                    httpEntity,
                    Object.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return "redirect:/admin/managerProductAdmin";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:eror1";

    }

    @SneakyThrows
    @PostMapping("/admin/editproduct")
    public String editProduct(HttpServletRequest request,@ModelAttribute ProductInput product) {
        HttpSession session = request.getSession();
        String token = (String) session.getAttribute("token");
        if (token == null ) {
            System.out.println("chua login");
            return "redirect:/login";
        }

        if (product.getId() == null) {
            System.out.println("error id");
            return "redirect:eror1";
        }


        ObjectMapper mapper = new ObjectMapper();
        String object = mapper.writeValueAsString(product);
        String endPoint = "http://localhost:8082/core/product/"+product.getId()+ "/update";
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.setContentType(MediaType.APPLICATION_JSON);
        requestHeader.set("Authorization",token);
        HttpEntity<Object> httpEntity = new HttpEntity<>(object, requestHeader);
        System.out.println(object);
        ResponseEntity response;
        try {
            response = restTemplate.exchange(
                    endPoint,
                    HttpMethod.PUT,
                    httpEntity,
                    Object.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return "redirect:/admin/managerProductAdmin";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:eror1";

    }


    @PostMapping("/deleteSelectedItems")
    public ResponseEntity<String> deleteSelectedItems(@RequestBody List<Integer> itemIds) {
        HttpSession session = httpServletRequest.getSession();
        String token = (String) session.getAttribute("token");
//        if (token == null ) {
//            System.out.println("chua login");
//            return "redirect:/login";
//        }


        // Xoá các phần tử được chọn
        for (Integer id : itemIds) {
            Integer tmp = productService.deleteById(id);
        }
        return ResponseEntity.ok("Xoá thành công");
    }










    @GetMapping("/admin/get-all")
    public List<ProductDetailOutput> getall(HttpServletRequest request){
        //HttpSession session = request.getSession();
        //String token = (String) session.getAttribute("token");
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwidXNlcl9pZCI6IjEiLCJwaG9uZSI6IjAxMjM0NTY3OCIsImV4cCI6MTY4NTgwMTMxMCwiaWF0IjoxNjgzMjA5MzEwLCJlbWFpbCI6ImRhb3Ryb25ncGh1YzUwMjA0MDAyQGdtYWlsLmNvbSJ9.9GBV1xfn4p66D3vS7YSv9xSUIFlm2Sm7rRqgKzFb6UmFaWm-vAxkLga0E7oHicYInHZvZriZTbZs8y2ZpLQ-bQ";
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


}
