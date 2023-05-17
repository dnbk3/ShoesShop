package vn.btl.hdvcoreservice.interceptor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class Payload {
    private Long customerId;
    private String phone;
    private String token;
}
