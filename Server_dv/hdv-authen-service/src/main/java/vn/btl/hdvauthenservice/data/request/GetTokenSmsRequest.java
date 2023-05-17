package vn.btl.hdvauthenservice.data.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetTokenSmsRequest {
    String client_id;
    String client_secret;
    String scope="send_brandname_otp send_brandname";
    String session_id;
    String grant_type="client_credentials";
}
