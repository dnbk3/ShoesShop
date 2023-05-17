package vn.btl.hdvcoreservice.repository.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="product")
public class Product {

	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;

	@Column(name="name")
	private String name;

	@Column(name="description")
	private String description;

	@Column(name="pictures")
	private String pictures;

	@Column(name="size")
	private Integer size;

	@Column(name="discount")
	private Long discount;

	@Column(name="quantity")
	private Integer quantity;

	@Column(name="price")
	private Long price;

	@Column(name="supplier_id")
	private Integer supplierId;

	@Column(name="category_id")
	private Integer categoryId;

	@Column(name="brand_id")
	private Integer brandId;

}
