package com.example.demo.Service;

import java.util.*;

import com.example.demo.Controller.Output.CustomerDetailOutput;
import com.example.demo.Controller.Output.CustomerOutput;
import com.example.demo.Controller.Output.ProductDetailOutput;
import com.example.demo.Controller.input.CustomerDetailInput;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Model.Customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Service
public class CustomerService {

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	private RestTemplate restTemplate;

	ObjectMapper objectMapper = new ObjectMapper();

	public List<CustomerDetailInput> GetAll(){

		HttpSession session = httpServletRequest.getSession();
		String token = (String) session.getAttribute("token");
		//String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwidXNlcl9pZCI6IjEiLCJwaG9uZSI6IjAxMjM0NTY3OCIsImV4cCI6MTY4NTk1NTQwNSwiaWF0IjoxNjgzMzYzNDA1LCJlbWFpbCI6ImRhb3Ryb25ncGh1YzUwMjA0MDAyQGdtYWlsLmNvbSJ9.Ea9HpCVqRM_bjGEC05xPkPMh6SX7A2-Y_XsFU-TuKm4x5sASkQGHeDTJLB59I5s6m9uUEmYrr-HBWyJLxoMNJg";
		if (token == null) {
			System.out.println("chua login");
			return null;
		}
		String endPoint = "http://localhost:8082/core/customers/get-all";
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
				List<CustomerDetailInput> list = objectMapper.readValue(jsonObject
						.getJSONArray("data").toString(), new TypeReference<List<CustomerDetailInput>>() {});
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
		//String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwidXNlcl9pZCI6IjEiLCJwaG9uZSI6IjAxMjM0NTY3OCIsImV4cCI6MTY4NjE0NTAwNCwiaWF0IjoxNjgzNTUzMDA0LCJlbWFpbCI6ImRhb3Ryb25ncGh1YzUwMjA0MDAyQGdtYWlsLmNvbSJ9.79vZD3qAwUkOCLUHVGR5tC8EbpTvGqNkAHzhLX8SsLf4dd7-0IJjasrLX45ZuTa0x4wKdrTehPU--38mZ3jrjQ";
		if (token == null) {
			System.out.println("chua login");
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();

		String endPoint = "http://localhost:8082/core/customers/"+id;
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

	public Customer GetCustomerById(int cus_id){
		String url ="http://localhost:8084/customer/getCustomer/{id}";
//		ResponseEntity<Customer> response = restTemplate.getForEntity(url, Customer.class);
		ResponseEntity<Customer> response = restTemplate.getForEntity(url, Customer.class,cus_id);
		Customer cus = response.getBody();
		return cus;
	}

	public CustomerOutput checkCustomer(String username, String password) {
		Map<String, Object> object = new HashMap<>();
		object.put("username", username);
		object.put("password", password);

		String endPoint = "http://localhost:8082/auth/v2/user/login";
		HttpHeaders requestHeader = new HttpHeaders();
		requestHeader.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> httpEntity = new HttpEntity<>(object, requestHeader);
		ResponseEntity response;
		try {
			response = restTemplate.exchange(
					endPoint,
					HttpMethod.POST,
					httpEntity,
					Object.class);
			if (response.getStatusCode() == HttpStatus.OK) {
//				String body = objectMapper.writeValueAsString(response.getBody());
//				JSONObject jsonObject = new JSONObject(body);
//				JSONObject data = jsonObject.getJSONObject("data");
//				String token = data.getString("token");
				// lưu token vào cokie
				String body = objectMapper.writeValueAsString(response.getBody());
				JSONObject jsonObject = new JSONObject(body);
				CustomerOutput res = objectMapper.readValue(jsonObject
						.getJSONObject("data").toString(), new TypeReference<CustomerOutput>() {});
				System.out.println(res);





				return res;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
