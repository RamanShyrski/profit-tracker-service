package com.shyrski.profit.tracker.client.config;

import org.springframework.beans.factory.annotation.Value;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class OpenSeaClientInterceptor implements RequestInterceptor {
    private static final String TOKEN_HEADER = "X-API-KEY";

    @Value("${opensea.api-key}")
    private String openSeaApiKey;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(TOKEN_HEADER, openSeaApiKey);
    }
}
