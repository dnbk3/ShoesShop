package vn.btl.hdvauthenservice.interceptor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Payload {

    private Long customerId;
    private String phone;
    private String token;
}
