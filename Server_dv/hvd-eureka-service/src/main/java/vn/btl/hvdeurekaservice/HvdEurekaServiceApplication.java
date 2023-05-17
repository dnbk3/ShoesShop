package vn.btl.hvdeurekaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class HvdEurekaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HvdEurekaServiceApplication.class, args);
    }

}
