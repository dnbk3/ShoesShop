package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shipper {
	private int ship_id;
	private String ship_name;
	private String ship_sex;
	private String ship_company_name;
	private String ship_phone;

	public int getShip_id() {
		return ship_id;
	}

	public void setShip_id(int ship_id) {
		this.ship_id = ship_id;
	}

	public String getShip_name() {
		return ship_name;
	}

	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}

	public String getShip_sex() {
		return ship_sex;
	}

	public void setShip_sex(String ship_sex) {
		this.ship_sex = ship_sex;
	}

	public String getShip_company_name() {
		return ship_company_name;
	}

	public void setShip_company_name(String ship_company_name) {
		this.ship_company_name = ship_company_name;
	}

	public String getShip_phone() {
		return ship_phone;
	}

	public void setShip_phone(String ship_phone) {
		this.ship_phone = ship_phone;
	}
}
