package vn.btl.hdvauthenservice.data.sql.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "otp_message")
public class OtpMessageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "otp_id", nullable = false)
    private Long otpId;

    @Column(name = "message")
    private String message;

    @Column(name = "identity", nullable = false)
    private String identity;

    @Column(name = "send_type")
    private String sendType;

    @Column(name = "source")
    private String source;

    @Column(name = "success")
    private boolean success = false;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

}
