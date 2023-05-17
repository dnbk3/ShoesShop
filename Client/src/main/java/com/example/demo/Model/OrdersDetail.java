package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDetail {
	private String productName;
	private String productImage;
	private Long productPrice;
	private Integer quantity;
	private Long amount;
}
