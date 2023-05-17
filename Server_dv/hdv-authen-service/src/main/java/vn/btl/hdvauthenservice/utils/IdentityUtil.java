package vn.btl.hdvauthenservice.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdentityUtil {


    private static final String EMAIL_REGEX = "^(.+)@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);


    public static IdentityType getIdentityType(String identity) {
        Matcher matcher = EMAIL_PATTERN.matcher(identity);
        if (matcher.matches()) {
            return IdentityType.EMAIL;
        } else {
            return IdentityType.PHONE;
        }
    }
}

