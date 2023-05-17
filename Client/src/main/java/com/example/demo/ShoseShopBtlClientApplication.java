package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ShoseShopBtlClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoseShopBtlClientApplication.class, args);
	}
	
	@Configuration
	public class RestConfig {
	 
	    @Bean
	    public RestTemplate restTemplate() {
	        return new RestTemplate();
	    }
	}
//	@Configuration
//	public class SecurityConfig{
//
//		@Bean
//		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//			http.authorizeRequests()
//			.anyRequest().authenticated()
//			.and().oauth2Login();
//			return http.build();
//		}
//	}
	
}
