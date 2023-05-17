package com.example.demo.Model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
	private int orderId;
	private long totalAmount;
	private String createdDate;
	private String paymentMethod;
	private int status;
	private long shippingPrice;
	private String note;
	private String address;
	private String orderCode;
	private String phone;
	private Integer shipperId;
	private String shipperName;
	private String customerName;
	private String customerPhone;
	private Integer customerId;
	private Integer approveBy;
	private String approvedName;
	private String approvedPhone;
	private String ngaymua;
}
