package com.example.demo.Controller;

import com.example.demo.Controller.Output.CustomerDetailOutput;
import com.example.demo.Controller.Output.CustomerOutput;
import com.example.demo.Controller.Output.OrderResponseWithDetails;
import com.example.demo.Controller.Output.ProductDetailOutput;
import com.example.demo.Controller.input.CustomerDetailInput;
import com.example.demo.Model.*;
import com.example.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {
	@Autowired
	HttpServletRequest request;

	@Autowired
	RegionService regionService;

	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	SupplierService supplierService;

	@Autowired
	CustomerService customerService;

	@Autowired
	BrandService brandService;

	@Autowired
	OrderService orderService;

	@GetMapping("/")
	public String index() {
		return "login";
	}
	@GetMapping("/login")
	public String Login() {
		return "login";
	}
	@GetMapping("/register")
	public String Register() {
		return "register";
	}
	@GetMapping("/adminFind")
	public String AdminFind() {
		return "adminFind";
	}
	@GetMapping("/cart")
	public String Cart() {
		return "cart";
	}
	@GetMapping("/detail")
	public String Detail() {
		return "detail";
	}
	@GetMapping("/editProduct")
	public String EditProduct() {
		return "EditProduct";
	}
	@GetMapping("/index")
	public String Index() {
		return "Login";
	}
	@GetMapping("customer/index1")
	public String CustomerIndex1() {
		return "index1";
	}
	@GetMapping("/customer/managerOrderDetailCustomer")
	public String ManagerOrderDetailCustomer() {
		return "ManagerOrderDetailCustomer";
	}
	@GetMapping("/managerOrderAdmin")
	public String ManagerOrderAdmin() {
		return "ManagerOrderAdmin";
	}
	@GetMapping("/customer/managerOrderCustomer")
	public String ManagerOrderCustomer() {
		return "ManagerOrderCustomer";
	}
	@GetMapping("/managerOrderDetailAdmin")
	public String ManagerOrderDetailAdmin() {
		return "ManagerOrderDetailAdmin";
	}
	@GetMapping("/admin/managerProductAdmin")
	public String ManagerProductAdmin(Model model) {
		List<ProductDetailOutput> list = productService.GetAllProduct();
		model.addAttribute("products",list);

		List<Category> listCategory = categoryService.GetAll();
		model.addAttribute("categories",listCategory);

		List<Supplier> listSupplier = supplierService.GetAll();
		model.addAttribute("suppliers",listSupplier);

		List<Brand> listBrand = brandService.GetAll();
		model.addAttribute("brands",listBrand);
		return "ManagerProductAdmin";
	}
//	@GetMapping("/managerUserAdmin")
//	public String ManagerUserAdmin() {
//		return "ManagerUserAdmin";
//	}
	@GetMapping("/myAccount")
	public String MyAccount(Model model) {

		List<Category> listCategory = categoryService.GetAll();
		model.addAttribute("categories",listCategory);

		HttpSession session = request.getSession();
		CustomerOutput user = (CustomerOutput) session.getAttribute("user");
		model.addAttribute("user",user);
		System.out.println(user);

		return "MyAccount";
	}


	@GetMapping("/pay")
	public String Pay() {
		return "pay";
	}
	@GetMapping("/viewOrderDetailStaff")
	public String ViewOrderDetailStaff(@RequestParam(value = "orderId", required = true) Long idOrder,
									   Model model) {
		List<Category> listCategory = categoryService.GetAll();
		model.addAttribute("categories",listCategory);

		OrderResponseWithDetails order = orderService.getAllOrdersDetail(idOrder);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		order.getOrderResponse().setNgaymua(formatter.format(new Date(Long.parseLong(order.getOrderResponse().getCreatedDate()))));
		System.out.println(order);
		model.addAttribute("order",order);
		return "ViewOrderDetailStaff";
	}
	@GetMapping("/viewOrderStaff")
	public String ViewOrderStaff(Model model) {

		List<Category> listCategory = categoryService.GetAll();
		model.addAttribute("categories",listCategory);

		List<Orders> list = orderService.GetAll();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		for(Orders i:list){
			System.out.println(i.getCreatedDate());

			i.setNgaymua(formatter.format(new Date(Long.parseLong(i.getCreatedDate()))));
		}
		model.addAttribute("orders",list);
		return "ViewOrderStaff";
	}
	@GetMapping("/admin/index1")
	public String adminIndex(@RequestParam(value = "categoryid", required = false) Integer idCate, Model model) {
		if(idCate == null){
			List<ProductDetailOutput> list = productService.GetAllProduct();
			model.addAttribute("products",list);
			model.addAttribute("checked","option0");
		}
		else{
			List<ProductDetailOutput> list = productService.GetAllProductByCategoryId(idCate);
			model.addAttribute("products",list);
			model.addAttribute("checked",idCate);
		}

		List<Category> listCategory = categoryService.GetAll();
		model.addAttribute("categories",listCategory);

		return "adminIndex1";
	}

	@GetMapping("/error")
	public String error1() {
		return "error";
	}
	@GetMapping("/admin/managerUserAdmin")
	public String ManagerUserAdmin(Model model) {

		List<Category> listCategory = categoryService.GetAll();
		model.addAttribute("categories",listCategory);

		List<Region> listTinh = regionService.GetAllByParentId(0);
		model.addAttribute("listTinh",listTinh);

		List<Region> listHuyen = regionService.GetAllByParentId(1);
		model.addAttribute("listHuyen",listHuyen);

		List<Region> listXa = regionService.GetAllByParentId(4);
		model.addAttribute("listXa",listXa);

		List<CustomerDetailInput> listCus = customerService.GetAll();
		model.addAttribute("customers",listCus);
		return "ManagerUserAdmin";
	}
}
