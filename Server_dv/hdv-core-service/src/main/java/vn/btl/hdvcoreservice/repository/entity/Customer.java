package vn.btl.hdvcoreservice.repository.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerId;
	
//	@Column(name="role")
//	private String role;

	@Column(name="name")
	private String name;

	@Column(name="sex")
	private String sex;

	@Column(name="address")
	private String address;

	@Column(name="phone")
	private String phone;

	@Column(name="email")
	private String email;

	@Column(name="password")
	private String password;

	@Column(name="shipAddress")
	private String shipAddress;

	@Column(name="money")
	private long money;
}
