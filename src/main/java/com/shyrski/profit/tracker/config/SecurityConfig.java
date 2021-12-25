package com.shyrski.profit.tracker.config;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends ResourceServerConfigurerAdapter {

    private final ResourceServerProperties resource;

    private static final List<String> WHITELISTED_ENDPOINTS = List.of("/actuator/health",
            "/v2/api-docs",
            "/swagger-ui.html",
            "/swagger-resources",
            "/swagger-resources/**",
            "/webjars/**");

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors();

        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers(WHITELISTED_ENDPOINTS.toArray(new String[0]))
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    @Bean
    public TokenStore jwkTokenStore() {
        return new JwkTokenStore(
                Collections.singletonList(resource.getJwk().getKeySetUri()),
                new CognitoAccessTokenConverter(),
                null);
    }
}
