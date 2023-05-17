package vn.btl.hdvauthenservice.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OtpSmsRequest {
    @JsonProperty("access_token")
    String access_token;
    @JsonProperty("session_id")
    String session_id;

    @JsonProperty("BrandName")
    String BrandName;

    @JsonProperty("Phone")
    String Phone;

    @JsonProperty("Message")
    String Message;

    @JsonProperty("RequestId")
    String RequestId;
}
