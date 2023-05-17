package vn.btl.hdvauthenservice.service;

import vn.btl.hdvauthenservice.data.request.OtpGenerationRequest;
import vn.btl.hdvauthenservice.data.request.OtpRegenerationRequest;
import vn.btl.hdvauthenservice.data.request.OtpVerifyRequest;
import vn.btl.hdvauthenservice.data.response.OtpResponse;

public interface OtpService {
    OtpResponse generateOtp(OtpGenerationRequest request);

    OtpResponse regenerateOtp(OtpRegenerationRequest request);

    boolean verifyOtp(OtpVerifyRequest request);

    boolean verifyOtp(String identity, String otpReferenceId);
}
