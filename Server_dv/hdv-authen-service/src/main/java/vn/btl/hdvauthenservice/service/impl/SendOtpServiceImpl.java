package vn.btl.hdvauthenservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.btl.hdvauthenservice.config.EmailConfiguration;
import vn.btl.hdvauthenservice.config.SmsConfig;
import vn.btl.hdvauthenservice.data.sql.model.OtpMessageEntity;
import vn.btl.hdvauthenservice.data.sql.repository.OtpMessageRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import vn.btl.hdvauthenservice.service.SendOtpService;
import vn.btl.hdvauthenservice.utils.IdentityType;

import java.util.Date;

@Service
@Slf4j
public class SendOtpServiceImpl implements SendOtpService {

    @Autowired
    ObjectMapper mapper;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${app.tele_url}")
    String urlTele;
    @Value("${app.tele_chat_id}")
    String chatIdTele;
    @Value("${app.otp.tele.template}")
    String templateTele;

    @Value("${app.otp.tele.template.test}")
    String templateTeleTest;

    @Value("${otp.mode}")
    String otpMode;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    EmailConfiguration emailConfiguration;

    @Autowired
    OtpMessageRepository otpMessageRepository;

    @Autowired
    SmsConfig smsConfig;


    @Override
    public boolean sendToPhone(Long otpId, String phone, String otpCode, String sendType) {
        String content = buildContentMessage(phone, otpCode, IdentityType.PHONE);
        OtpMessageEntity otpMessage = null;
        boolean isSuccess = false;
        String contentTele = String.format(templateTeleTest, otpCode, phone);
        otpMessage = logOtpMessage(otpId, phone, "TELE", null, contentTele);
        isSuccess = sendMessageViaTele(contentTele);
        updateStatusLogOtpMessage(otpMessage, isSuccess);
        return isSuccess;
    }

    @Override
    public boolean sendToEmail(Long otpId, String email, String otpCode, String sendType) {
        String content = buildContentMessage(email, otpCode, IdentityType.EMAIL);
        OtpMessageEntity otpMessage = null;
        boolean isSuccess = false;
        String contentTele = String.format(templateTeleTest, otpCode, email);
        otpMessage = logOtpMessage(otpId, email, "TELE", null, contentTele);
        isSuccess = sendMessageViaTele(contentTele);
        sendMessageEmail(content, email);
        updateStatusLogOtpMessage(otpMessage, isSuccess);
        return isSuccess;
    }

    private boolean sendMessageViaTele(String message) {
        String urlSend = String.format(urlTele, chatIdTele, message);
        log.info("Sending tele to url [{}]", urlSend);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity request = new HttpEntity(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(urlSend,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<String>() {
                });
        log.info("Received response [{}]", response.getBody());
        return true;
    }

    private String buildContentMessage(String identity, String otpCode, IdentityType identityType) {
        String template;
        if (identityType == IdentityType.PHONE) {
            template = smsConfig.getOtpPhoneTemplate();
        } else {
            template = smsConfig.getOtpEmailTemplate();
        }
        return String.format(template, otpCode, identity);
    }

    private boolean sendMessageEmail(String message, String email) {
        try {
            log.info("send message to email {}", email);
            MimeMessage mineMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mineMessage);
            helper.setFrom("huongdichvu2023@gmail.com");
            helper.setTo(email);
            helper.setSubject("Security");
            helper.setText(message);
            mailSender.send(mineMessage);
            return true;
        }catch (MessagingException e){
            e.printStackTrace();
            return false;
        }
    }

    private OtpMessageEntity logOtpMessage(Long otpId, String identity, String sentType, String source, String message) {
        OtpMessageEntity entity = new OtpMessageEntity();
        entity.setOtpId(otpId);
        entity.setIdentity(identity);
        entity.setMessage(message);
        entity.setSendType(sentType);
        entity.setSource(source);
        entity.setCreatedDate(new Date());
        entity.setUpdatedDate(new Date());
        return otpMessageRepository.save(entity);
    }

    private void updateStatusLogOtpMessage(OtpMessageEntity entity, boolean isSuccess) {
        if (entity != null) {
            entity.setSuccess(isSuccess);
            entity.setUpdatedDate(new Date());
            otpMessageRepository.save(entity);
        }
    }
}
