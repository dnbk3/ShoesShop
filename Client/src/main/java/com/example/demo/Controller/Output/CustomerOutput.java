package com.example.demo.Controller.Output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerOutput {
	private String token;
	private long customerId;
	private String name;
	private String phone;
	private String email;
	private String avatar;
	private boolean isActive;
	private Date birthday;
	private Date createdDate;
	private Date modifiedDate;
	private Date tokenExpire;
	private String chatToken;
}
