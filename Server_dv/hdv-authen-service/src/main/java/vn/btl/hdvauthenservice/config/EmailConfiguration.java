package vn.btl.hdvauthenservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class EmailConfiguration {
    @Value("${email.token.expired.time.in.hours}")
    private Integer verifyExpire;

    @Value("${email.pattern}")
    private String emailPattern;

//    @Value("${email.notify.api}")
//    private String emailNotifyApi;
//
//    @Value("${email.address.verify}")
//    private String addressVerify;
//
//    @Value("${email.address.forgot-pass)")
//    private String addressForgotPass;
}
