package com.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
//@EnableEurekaClient
@EntityScan(basePackages = "com.food.FoodModel.Account")
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

        //This encodes password for users when registering
          @Bean
           PasswordEncoder passwordEncoder() {
           return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
}
