package vn.btl.hdvauthenservice.data.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OtpGenerationRequest {
    private String username;
    private String type;
}
