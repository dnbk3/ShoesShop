package vn.btl.hdvcoreservice.repository.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="shop_order")
public class Order {

	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;

	@Column(name="total_amount")
	private long totalAmount;

	@Column(name="created_date")
	private Date createdDate;

	@Column(name="payment_method")
	private String paymentMethod;

	@Column(name = "status")
	private int status;

	@Column(name = "shipping_price")
	private long shippingPrice;

	@Column(name = "note")
	private String note;

	@Column(name = "address")
	private String address;

	@Column(name = "order_code")
	private String orderCode;

	@Column(name = "phone")
	private String phone;

	@Column(name="shipper_id")
	private Integer shipperId;

	@Column(name="customer_id")
	private Integer customerId;

	@Column(name = "approved_by")
	private Integer approvedBy;
}
