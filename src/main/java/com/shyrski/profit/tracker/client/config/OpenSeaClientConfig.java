package com.shyrski.profit.tracker.client.config;

import org.springframework.context.annotation.Bean;

import feign.RequestInterceptor;

public class OpenSeaClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new OpenSeaClientInterceptor();
    }
}
