package vn.btl.hdvcoreservice.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="supplier")
public class Supplier {

	@Id
	@Column(name = "supplier_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int supplierId;

	@Column(name="name")
	private String name;

	@Column(name="address")
	private String address;

	@Column(name="email")
	private String email;

	@Column(name="phone")
	private String phone;

	@Column(name="payment_method")
	private String paymentMethod;
}
