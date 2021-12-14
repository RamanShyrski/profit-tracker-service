package com.shyrski.profit.tracker.config;

import java.util.Map;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

@Component
public class CognitoAccessTokenConverter extends JwtAccessTokenConverter {

    private static final String COGNITO_USERNAME = "username";
    private static final String SPRING_USER_NAME = "user_name";

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
        if (claims.containsKey(COGNITO_USERNAME))
            ((Map<String, Object>) claims).put(SPRING_USER_NAME, claims.get(COGNITO_USERNAME));
        return super.extractAuthentication(claims);
    }
}
