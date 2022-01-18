package com.shyrski.profit.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableFeignClients
@EnableResourceServer
@SpringBootApplication
public class ProfitTrackerService {

    public static void main(String[] args) {
        SpringApplication.run(ProfitTrackerService.class, args);
    }
}
