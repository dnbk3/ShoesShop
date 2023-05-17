package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {

	private int supplierId;


	private String name;

	private String address;

	private String email;

	private String phone;

	private String paymentMethod;
}
