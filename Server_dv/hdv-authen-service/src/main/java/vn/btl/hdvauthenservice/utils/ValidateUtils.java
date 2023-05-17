package vn.btl.hdvauthenservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.btl.hdvauthenservice.config.OtpConfiguration;
import vn.btl.hdvauthenservice.exceptions.AuthenErrorCode;
import vn.btl.hdvauthenservice.exceptions.AuthenException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class ValidateUtils {
    @Autowired
    OtpConfiguration otpConfiguration;

    public void mobileNumberVerify(String mobileNumber) {
        if (StringUtils.isBlank(mobileNumber)) {
            log.error("Mobile number can not be [empty] or [blank]");
            throw new AuthenException(AuthenErrorCode.INVALID_MOBILE_NUMBER_FORMAT);
        }
        validateMobilePattern(mobileNumber);
    }

    private void validateMobilePattern(String mobileNumber) {
        if (mobileNumber != null) {
            Pattern pattern = Pattern.compile(otpConfiguration.getMobileNumberFormat());
            Matcher matcher = pattern.matcher(mobileNumber);
            if (!matcher.find()) {
                log.error("The [{}] mobile number is malformed.", mobileNumber);
                throw new AuthenException(AuthenErrorCode.INVALID_MOBILE_NUMBER_FORMAT);
            }
        }
    }

    public void emailVerify(String email) {
        if (StringUtils.isBlank(email)) {
            log.error("Email can not be [empty] or [blank]");
            throw new AuthenException(AuthenErrorCode.INVALID_EMAIL_FORMAT);
        }
        validateEmailPattern(email);
    }

    private void validateEmailPattern(String email) {
        if (email != null) {
            Pattern pattern = Pattern.compile(otpConfiguration.getEmailFormat());
            Matcher matcher = pattern.matcher(email);
            if (!matcher.find()) {
                log.error("The [{}] email is malformed.", email);
                throw new AuthenException(AuthenErrorCode.INVALID_EMAIL_FORMAT);
            }
        }
    }

    public void passwordVerify(String password) {
        if (StringUtils.isBlank(password)) {
            log.error("Missing password when create customer identity");
            throw new AuthenException(AuthenErrorCode.INVALID_PASSWORD);
        }
        isValidPassword(password);
    }

    public void passwordVerifyLogin(String password) {
        if (StringUtils.isBlank(password)) {
            log.error("Missing password when create customer identity");
            throw new AuthenException(AuthenErrorCode.INVALID_PASSWORD);
        }
    }

    public static boolean isValidPassword(String password) {
        // Password Validation Rule
        // at least 1 letter
        // at least 1 number
        // at least 6 character length
//        String pattern = "(?=.*[a-zA-Z])(?=.*[0-9]).{6,}";
//
//        if (password == null || !password.matches(pattern)) {
//            log.error("Password value [{}] is invalid pattern", password);
//            throw new AuthenException(AuthenErrorCode.INVALID_PASSWORD);
//        }
        if (password == null || password.length() < 6) {
            log.error("Password value [{}] is invalid pattern", password);
            throw new AuthenException(AuthenErrorCode.INVALID_PASSWORD);
        }
        return true;
    }
}
