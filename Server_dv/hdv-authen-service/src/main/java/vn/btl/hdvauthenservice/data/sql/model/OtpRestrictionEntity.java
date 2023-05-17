package vn.btl.hdvauthenservice.data.sql.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "otp_restriction")
public class OtpRestrictionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * mã người dùng (chỉ sử dụng khi dùng quên mật khẩu)
     */
    @Column(name = "user_id")
    @ApiModelProperty("mã người dùng (chỉ sử dụng khi dùng quên mật khẩu)")
    private Long userId;

    /**
     * định danh người dùng
     */
    @ApiModelProperty("định danh người dùng")
    @Column(name = "identity", nullable = false)
    private String identity;

    /**
     * loại otp (RESGISTRATION, FOGET PASSWORD)
     */
    @Column(name = "type")
    @ApiModelProperty("loại otp (RESGISTRATION, FOGET PASSWORD)")
    private String type;

    /**
     * trạng thái xóa
     */
    @Column(name = "deleted")
    @ApiModelProperty("trạng thái xóa")
    private boolean deleted = false;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

}
