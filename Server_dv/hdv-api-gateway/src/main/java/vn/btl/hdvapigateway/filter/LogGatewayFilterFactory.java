package vn.btl.hdvapigateway.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class LogGatewayFilterFactory extends AbstractGatewayFilterFactory<LogGatewayFilterFactory.Config> {

    public LogGatewayFilterFactory(){
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest req = exchange.getRequest();
            Long startTime = System.currentTimeMillis();
            String customerId = (req.getHeaders().get("user_id") != null && req.getHeaders().get("user_id").size() > 0) ? req.getHeaders().get("user_id").get(0) : null;
            String phone = (req.getHeaders().get("phone") != null && req.getHeaders().get("phone").size() > 0) ? req.getHeaders().get("phone").get(0) : null;
            String path = req.getPath().value();
            String reqId = req.getId();
            String ipClient = req.getRemoteAddress() != null ? req.getRemoteAddress().getHostString() : null;
            String method = req.getMethodValue();
            log.info("Request [{}] from ip {} : {} : {} : by customer {} and phone {}", reqId, ipClient, method, path, customerId, phone);

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                        Long endTime = System.currentTimeMillis();
                        Integer httpStatusCode = exchange.getResponse().getStatusCode() != null ? exchange.getResponse().getRawStatusCode() : null;

                        log.info("Request [{}] complete [{}] duration: {}ms", reqId, httpStatusCode, endTime - startTime);
                    }
            ));
        });
    }

    @Data
    @AllArgsConstructor
    public static class Config{

    }
}
