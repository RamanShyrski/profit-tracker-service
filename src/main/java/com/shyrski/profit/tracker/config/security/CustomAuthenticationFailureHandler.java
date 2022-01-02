package com.shyrski.profit.tracker.config.security;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shyrski.profit.tracker.exception.ExceptionDetails;
import com.shyrski.profit.tracker.exception.message.ExceptionMessages;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(ContentType.APPLICATION_JSON.toString());

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .status(UNAUTHORIZED)
                .message(ExceptionMessages.UNAUTHORIZED)
                .timestamp(LocalDateTime.now())
                .build();

        response.getOutputStream()
                .println(objectMapper.writeValueAsString(exceptionDetails));
    }
}
