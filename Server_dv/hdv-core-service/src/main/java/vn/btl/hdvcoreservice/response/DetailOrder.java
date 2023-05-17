package vn.btl.hdvcoreservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailOrder {

    OrderResponse orderResponse;
    List<OrderDetailResponse> orderDetails;
}
