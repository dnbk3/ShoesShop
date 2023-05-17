package vn.btl.hdvauthenservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class SmsConfig {
    @Value("${app.otp.phone.template}")
    private String otpPhoneTemplate;

    @Value("${app.otp.email.template}")
    private String otpEmailTemplate;
}
