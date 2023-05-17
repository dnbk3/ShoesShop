package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Customer {

	private int cus_id;
	private String cus_name;
	private String cus_sex;
	private String cus_role;
	private String cus_address;
	private String cus_phone;
	private String cus_email;
	private String cus_password;
	private String cus_ship_address;
	private long cus_money;

	public int getCus_id() {
		return cus_id;
	}

	public void setCus_id(int cus_id) {
		this.cus_id = cus_id;
	}

	public String getCus_name() {
		return cus_name;
	}

	public void setCus_name(String cus_name) {
		this.cus_name = cus_name;
	}

	public String getCus_sex() {
		return cus_sex;
	}

	public void setCus_sex(String cus_sex) {
		this.cus_sex = cus_sex;
	}

	public String getCus_role() {
		return cus_role;
	}

	public void setCus_role(String cus_role) {
		this.cus_role = cus_role;
	}

	public String getCus_address() {
		return cus_address;
	}

	public void setCus_address(String cus_address) {
		this.cus_address = cus_address;
	}

	public String getCus_phone() {
		return cus_phone;
	}

	public void setCus_phone(String cus_phone) {
		this.cus_phone = cus_phone;
	}

	public String getCus_email() {
		return cus_email;
	}

	public void setCus_email(String cus_email) {
		this.cus_email = cus_email;
	}

	public String getCus_password() {
		return cus_password;
	}

	public void setCus_password(String cus_password) {
		this.cus_password = cus_password;
	}

	public String getCus_ship_address() {
		return cus_ship_address;
	}

	public void setCus_ship_address(String cus_ship_address) {
		this.cus_ship_address = cus_ship_address;
	}

	public long getCus_money() {
		return cus_money;
	}

	public void setCus_money(long cus_money) {
		this.cus_money = cus_money;
	}
}
