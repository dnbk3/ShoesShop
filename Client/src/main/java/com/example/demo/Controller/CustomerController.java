package com.example.demo.Controller;

import com.example.demo.Controller.input.CustomerInput;
import com.example.demo.Controller.Output.CustomerOutput;
import com.example.demo.Controller.input.ProductInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Model.ReCaptchaResponse;
import com.example.demo.Service.CustomerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	HttpServletRequest request;

	@PostMapping("/checklogin")
	public String Check(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam(name = "g-recaptcha-response") String captchaResponse) {
		String url = "https://www.google.com/recaptcha/api/siteverify";
		String params = "?secret=6Lfc-oMlAAAAAKKcjt8NjOFpeVvILH9Rbentg9oz&response=" + captchaResponse;
		ReCaptchaResponse reCaptchaResponse = restTemplate.exchange(url + params, HttpMethod.POST, null, ReCaptchaResponse.class).getBody();
		if (!reCaptchaResponse.isSuccess()) {
			return "redirect:/login";
		}
		CustomerOutput checkUser = customerService.checkCustomer(username, password);
		if (checkUser != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", checkUser);
			session.setAttribute("token", checkUser.getToken());
			return "redirect:/admin/index1";
		} else {
			return "redirect:/login";
		}
	}

	@GetMapping("/logout")
	public String logout(){
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/login";
	}

	@SneakyThrows
	@PostMapping("/admin/newcustomer")
	public String newCustomer(HttpServletRequest request,@ModelAttribute CustomerInput cus) {
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
		if (token == null) {
			System.out.println("chua login");
			return "redirect:/login";
		}
		ObjectMapper mapper = new ObjectMapper();
		String object = mapper.writeValueAsString(cus);
		String endPoint = "http://localhost:8082/core/customers";
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
				return "redirect:/admin/managerUserAdmin";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "redirect:eror1";
	}

	@SneakyThrows
	@PostMapping("/admin/editcustomer")
	public String editCustomer(HttpServletRequest request,@ModelAttribute CustomerInput cus) {
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
		if (token == null ) {
			System.out.println("chua login");
			return "redirect:/login";
		}

		if (cus.getCustomerId() == null) {
			System.out.println("error id");
			return "redirect:eror1";
		}


		ObjectMapper mapper = new ObjectMapper();
		String object = mapper.writeValueAsString(cus);
		String endPoint = "http://localhost:8082/core/customers/"+cus.getCustomerId();
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
				return "redirect:/admin/managerUserAdmin";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "redirect:eror1";

	}

	@PostMapping("/deleteSelectedUser")
	public ResponseEntity<String> deleteSelectedItems(@RequestBody List<Integer> itemIds) {
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
//        if (token == null ) {
//            System.out.println("chua login");
//            return "redirect:/login";
//        }


		// Xoá các phần tử được chọn
		for (Integer id : itemIds) {
			int tmp = customerService.deleteById(id);

		}
		return ResponseEntity.ok("Xoá thành công");
	}

}
