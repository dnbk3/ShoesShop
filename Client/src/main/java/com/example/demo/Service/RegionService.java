package com.example.demo.Service;


import com.example.demo.Model.Region;
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
public class RegionService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    private RestTemplate restTemplate;

    public List<Region> GetAllByParentId(int id){

        HttpSession session = httpServletRequest.getSession();
        String token = (String) session.getAttribute("token");
        //String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwidXNlcl9pZCI6IjEiLCJwaG9uZSI6IjAxMjM0NTY3OCIsImV4cCI6MTY4NjEwOTcyMiwiaWF0IjoxNjgzNTE3NzIyLCJlbWFpbCI6ImRhb3Ryb25ncGh1YzUwMjA0MDAyQGdtYWlsLmNvbSJ9.tcXNhF0CLRz4Ek9HyXZ08hATmAOf35tqXjIhBzVpAvk1oZEppyj4Mead1mxw8lbNnt9iJEuD1XGJzizNuCx3NQ";
        if (token == null) {
            System.out.println("chua login");
            return null;
        }
        String endPoint = "http://localhost:8082/core/customers/region?parentId=" + id;
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
                List<Region> list = objectMapper.readValue(jsonObject
                        .getJSONArray("data").toString(), new TypeReference<List<Region>>() {});
                System.out.println(list);
                return list;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }


}
