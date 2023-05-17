package vn.btl.hdvauthenservice.utils;

public enum IdentityType {
    PHONE("PHONE"), EMAIL("EMAIL");

    private String value;

    IdentityType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}