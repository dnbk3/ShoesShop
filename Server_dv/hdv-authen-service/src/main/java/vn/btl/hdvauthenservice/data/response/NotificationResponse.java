package vn.btl.hdvauthenservice.data.response;

import lombok.Data;

@Data
public class NotificationResponse {
    private String error;
    private Object message;
    private int statusCode;
}
