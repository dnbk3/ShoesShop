package vn.btl.hdvcoreservice.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UpdateSystemCustomerRequest {
    private String name;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private Long nationalId;
    private String address;
    private Long provinceId;
    private Long districtId;
    private Long wardId;
    private String provinceName;
    private String districtName;
    private String wardName;
}