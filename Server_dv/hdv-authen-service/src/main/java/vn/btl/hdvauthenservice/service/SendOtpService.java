package vn.btl.hdvauthenservice.service;

public interface SendOtpService {
    boolean sendToPhone(Long otpId, String phone, String otpCode, String sendType);
    boolean sendToEmail(Long otpId, String email, String otpCode, String sendType);
}
