package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	private int pro_id;
	private String pro_name;
	private String pro_description;
	private String pro_picture;
	private int pro_rank;
	private String pro_color;
	private int pro_size;
	private int pro_discount;
	private int pro_quantity;
	private long pro_price;
	private String pro_paterial;
	private String pro_note;
	private int sup_id;
	private int cat_id;

	public int getPro_id() {
		return pro_id;
	}

	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}

	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public String getPro_description() {
		return pro_description;
	}

	public void setPro_description(String pro_description) {
		this.pro_description = pro_description;
	}

	public String getPro_picture() {
		return pro_picture;
	}

	public void setPro_picture(String pro_picture) {
		this.pro_picture = pro_picture;
	}

	public int getPro_rank() {
		return pro_rank;
	}

	public void setPro_rank(int pro_rank) {
		this.pro_rank = pro_rank;
	}

	public String getPro_color() {
		return pro_color;
	}

	public void setPro_color(String pro_color) {
		this.pro_color = pro_color;
	}

	public int getPro_size() {
		return pro_size;
	}

	public void setPro_size(int pro_size) {
		this.pro_size = pro_size;
	}

	public int getPro_discount() {
		return pro_discount;
	}

	public void setPro_discount(int pro_discount) {
		this.pro_discount = pro_discount;
	}

	public int getPro_quantity() {
		return pro_quantity;
	}

	public void setPro_quantity(int pro_quantity) {
		this.pro_quantity = pro_quantity;
	}

	public long getPro_price() {
		return pro_price;
	}

	public void setPro_price(long pro_price) {
		this.pro_price = pro_price;
	}

	public String getPro_paterial() {
		return pro_paterial;
	}

	public void setPro_paterial(String pro_paterial) {
		this.pro_paterial = pro_paterial;
	}

	public String getPro_note() {
		return pro_note;
	}

	public void setPro_note(String pro_note) {
		this.pro_note = pro_note;
	}

	public int getSup_id() {
		return sup_id;
	}

	public void setSup_id(int sup_id) {
		this.sup_id = sup_id;
	}

	public int getCat_id() {
		return cat_id;
	}

	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}
}
