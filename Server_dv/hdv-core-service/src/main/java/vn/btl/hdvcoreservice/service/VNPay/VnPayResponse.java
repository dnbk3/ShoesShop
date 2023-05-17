package vn.btl.hdvcoreservice.service.VNPay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VnPayResponse {

    private String code;
    private String message;
}
