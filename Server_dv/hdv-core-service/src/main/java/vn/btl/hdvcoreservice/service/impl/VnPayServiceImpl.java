package vn.btl.hdvcoreservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URLEncoder;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.btl.hdvcoreservice.service.VNPay.VnPayRequest;
import vn.btl.hdvcoreservice.service.VnPayService;
import vn.btl.hdvcoreservice.utils.ResponseFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Log4j2
public class VnPayServiceImpl implements VnPayService {

    @Value("${vnp_Url}")
    private String vnpUrl;
    @Value("${vnp_HashSecret}")
    private String secret;
    @Value("${vnp_TmnCode}")
    private String code;
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public ResponseEntity payOrder(VnPayRequest request) throws Exception {
        String vnpVersion = "2.1.0";//bb
        String vnpCommand = "pay";//bb
        String tmnCode = code;//bb
        String orderInfo = request.getOrderInfo();//bb
        String orderType = "160000";//tc
        String txnRef = RandomStringUtils.random(8, false, true);//bb
        String ipAddr = request.getIpAddr(); //bb
        int amount = request.getAmount() * 100;//bb
        String bankCode = request.getBankCode(); //tc
        String locate = "vn";//bb
        String returnUrl = "http://localhost:8002/payment.html";//bb
        String currCode = "VND"; //bb
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String createDate = formatter.format(cld.getTime());//bb
        cld.add(Calendar.MINUTE, 15);
        String expireDate = formatter.format(cld.getTime());
        String builder = "vnp_Amount=%s&vnp_BankCode=%s&vnp_Command=%s&vnp_CreateDate=%s&vnp_CurrCode=%s&vnp_ExpireDate=%s&" +
                "vnp_IpAddr=%s&vnp_Locale=%s&vnp_OrderInfo=%s&vnp_OrderType=%s" +
                "&vnp_ReturnUrl=%s&vnp_TmnCode=%s&vnp_TxnRef=%s&vnp_Version=%s";
        String path = String.format(builder, encode(String.valueOf(amount)), encode(bankCode), encode(vnpCommand), encode(createDate), encode(currCode)
                , encode(expireDate), encode(ipAddr), encode(locate), encode(orderInfo), encode(orderType),
                encode(returnUrl), encode(tmnCode), encode(txnRef), encode(vnpVersion));
        log.info("path {}", path);

        String secureHash = encode(secret, path);
        log.info("hash :{}", secureHash);
        path += "&vnp_SecureHash=" + secureHash;
        String endPoint = vnpUrl + "?" + path;
        log.info("endpoint :{}", endPoint);
        return ResponseFactory.success(endPoint, String.class);
    }

    public String encode(String key, String data) throws Exception {
        Mac sha512_HMAC = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        sha512_HMAC.init(secretKeySpec);
        return Hex.encodeHexString(sha512_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }

    public String encode(String data) throws UnsupportedEncodingException {
        return URLEncoder.encode(data, StandardCharsets.US_ASCII.toString());
    }
}
