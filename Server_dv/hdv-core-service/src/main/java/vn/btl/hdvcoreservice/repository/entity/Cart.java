package vn.btl.hdvcoreservice.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cart_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "product_image", nullable = false)
    private String productImage;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "size", nullable = false)
    private Integer size;

}
