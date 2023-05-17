package vn.btl.hdvcoreservice.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "system_region")
public class SystemRegion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "dict_item_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dictItemId;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "parent_item_id")
    private Long parentItemId;

    @Column(name = "level")
    private Integer level;

}
