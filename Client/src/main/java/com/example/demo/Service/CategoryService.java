package com.example.demo.Service;

import java.util.Arrays;
import java.util.List;

import com.example.demo.Controller.Output.ProductDetailOutput;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Model.Category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class CategoryService {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	private RestTemplate restTemplate;

	public List<Category> GetAll(){

		HttpSession session = httpServletRequest.getSession();
		String token = (String) session.getAttribute("token");
		//String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwidXNlcl9pZCI6IjEiLCJwaG9uZSI6IjAxMjM0NTY3OCIsImV4cCI6MTY4NTk1NTQwNSwiaWF0IjoxNjgzMzYzNDA1LCJlbWFpbCI6ImRhb3Ryb25ncGh1YzUwMjA0MDAyQGdtYWlsLmNvbSJ9.Ea9HpCVqRM_bjGEC05xPkPMh6SX7A2-Y_XsFU-TuKm4x5sASkQGHeDTJLB59I5s6m9uUEmYrr-HBWyJLxoMNJg";
		if (token == null) {
			System.out.println("chua login");
			return null;
		}
		String endPoint = "http://localhost:8082/core/category";
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
				List<Category> list = objectMapper.readValue(jsonObject
						.getJSONArray("data").toString(), new TypeReference<List<Category>>() {});
				System.out.println(list);
				return list;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
