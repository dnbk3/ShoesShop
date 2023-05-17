package vn.btl.hdvcoreservice.repository.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="order_detail")
public class OrderDetail {

	@Id
	@Column(name = "order_detail_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderDetailId;

	@Column(name="order_id")
	private int orderId;

	@Column(name="product_id")
	private int productId;

	@Column(name = "product_name")
	private String productName;

	@Column(name ="product_image")
	private String productImage;

	@Column(name = "product_price")
	private long productPrice;

	@Column(name="quantity")
	private int quantity;

	@Column(name="amount")
	private long amount;

}
