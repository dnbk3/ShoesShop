package vn.btl.hdvauthenservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.btl.hdvauthenservice.data.request.OtpGenerationRequest;
import vn.btl.hdvauthenservice.data.request.OtpRegenerationRequest;
import vn.btl.hdvauthenservice.data.request.OtpVerifyRequest;
import vn.btl.hdvauthenservice.data.response.OtpResponse;
import vn.btl.hdvauthenservice.data.response.OtpVerificationResponse;
import vn.btl.hdvauthenservice.service.OtpService;
import vn.btl.hdvauthenservice.utils.ResponseFactory;


@RestController
@RequestMapping("/v2/otp")
@Slf4j
public class OtpController {
    @Autowired
    OtpService otpService;

    @PostMapping(value = "/generate")
    public ResponseEntity<OtpResponse> generateOtp(@RequestBody OtpGenerationRequest request) {
        log.info("Generate otp {} for: {}", request.getType(), request.getUsername());
        OtpResponse res = otpService.generateOtp(request);
        log.info("Otp: {}", res);
        return ResponseFactory.success(res, OtpResponse.class);
    }


    @PostMapping(value = "/regenerate")
    public ResponseEntity<OtpResponse> regenerateOtp(@RequestBody OtpRegenerationRequest request) {
        log.info("Regenerate otp {}", request.getOtpReferenceId());
        OtpResponse res = otpService.regenerateOtp(request);

        return ResponseFactory.success(res, OtpResponse.class);
    }

    @PostMapping(value = "/verify")
    public ResponseEntity<OtpVerificationResponse> verifyOtpCode(@RequestBody OtpVerifyRequest request) {
        log.info("Verify otp {}: {}", request.getOtpReferenceId(), request.getOtpCode());
        boolean res = otpService.verifyOtp(request);
        if (res)
            return ResponseFactory.success();
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
