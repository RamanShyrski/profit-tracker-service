package com.shyrski.profit.tracker.config;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.shyrski.profit.tracker.exception.ExceptionDetails;
import com.shyrski.profit.tracker.exception.ServerException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ExceptionDetails> handleException(ServerException exception) {
        log.error("Exception happened", exception);
        return new ResponseEntity<>(exception.getExceptionDetails(),
                exception.getExceptionDetails().getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDetails> handleException(RuntimeException exception) {
        ExceptionDetails exceptionDetails = buildDetails(exception, FORBIDDEN);
        return new ResponseEntity<>(exceptionDetails, FORBIDDEN);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionDetails> handleException(MissingServletRequestParameterException exception) {
        ExceptionDetails exceptionDetails = buildDetails(exception, BAD_REQUEST);
        return new ResponseEntity<>(exceptionDetails, BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionDetails> handleException(BindException exception) {
        String fieldName = Optional.ofNullable(exception.getFieldError()).map(FieldError::getField).orElse(null);

        if (isEmpty(fieldName)) {
            return new ResponseEntity<>(null, BAD_REQUEST);
        }

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .status(BAD_REQUEST)
                .message(fieldName + " " + exception.getFieldError().getDefaultMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionDetails, BAD_REQUEST);
    }

    private ExceptionDetails buildDetails(Exception exception, HttpStatus status) {
        return ExceptionDetails.builder()
                .status(status)
                .message(exception.getLocalizedMessage())
                .debugMessage(Arrays.toString(exception.getStackTrace()))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDetails> handleException(Exception exception) {
        log.error("Internal server error", exception);
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .status(INTERNAL_SERVER_ERROR)
                .message(exception.getLocalizedMessage())
                .debugMessage(Arrays.toString(exception.getStackTrace()))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(exceptionDetails, INTERNAL_SERVER_ERROR);
    }
}
