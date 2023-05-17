package vn.btl.hdvauthenservice.utils;

public enum OtpTypeEnum {
    REGISTRATION("REGISTRATION"),
    FORGOT_PASSWORD("FORGOT PASSWORD"),

    CREATE_CONTRACT("CREATE CONTRACT");

    private String value;
    OtpTypeEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}