package vn.btl.hdvauthenservice.data.sql.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "otp")
public class OtpEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * mã người dùng (chỉ sử dụng khi dùng quên mật khẩu)
     */
    @Column(name = "user_id")
    @ApiModelProperty("mã người dùng (chỉ sử dụng khi dùng quên mật khẩu)")
    private Long userId;

    /**
     * mã tham chiếu otp
     */
    @ApiModelProperty("mã tham chiếu otp")
    @Column(name = "otp_reference_id", nullable = false)
    private String otpReferenceId;

    /**
     * mã otp
     */
    @ApiModelProperty("mã otp")
    @Column(name = "otp_code", nullable = false)
    private String otpCode;

    /**
     * định danh người dùng
     */
    @Column(name = "identity")
    @ApiModelProperty("định danh người dùng")
    private String identity;

    /**
     * loại otp (RESGISTRATION, FOGET PASSWORD)
     */
    @Column(name = "type", nullable = false)
    @ApiModelProperty("loại otp (RESGISTRATION, FOGET PASSWORD)")
    private String type;

    /**
     * Hình thức gửi OTP (SMS, EMAIL, TELE, ALL)
     */
    @Column(name = "send_type")
    @ApiModelProperty("Hình thức gửi OTP (SMS, EMAIL, TELE, ALL)")
    private String sendType;

    /**
     * thời hạn otp
     */
    @Column(name = "expired_date")
    @ApiModelProperty("thời hạn otp")
    private Date expiredDate;

    /**
     * thời gian xác thực otp
     */
    @Column(name = "verified_date")
    @ApiModelProperty("thời gian xác thực otp")
    private Date verifiedDate;

    /**
     * Trạng thái sử dụng
     */
    @Column(name = "used")
    @ApiModelProperty("Trạng thái sử dụng")
    private boolean used;

    /**
     * trạng thái xóa
     */
    @Column(name = "deleted")
    @ApiModelProperty("trạng thái xóa")
    private boolean deleted = false;

    /**
     * trạng thái xác thực
     */
    @Column(name = "verified")
    @ApiModelProperty("trạng thái xác thực")
    private boolean verified = false;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

}
