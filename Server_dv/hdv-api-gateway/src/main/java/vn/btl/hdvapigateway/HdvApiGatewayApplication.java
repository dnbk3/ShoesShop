package vn.btl.hdvapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class HdvApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(HdvApiGatewayApplication.class, args);
    }

}
