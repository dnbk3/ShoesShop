package vn.btl.hdvcoreservice.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="shipper")
public class Shipper {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int shipperId;

	@Column(name="name")
	private String name;

	@Column(name="sex")
	private String sex;

	@Column(name="company_name")
	private String companyName;

	@Column(name="phone")
	private String phone;
}
