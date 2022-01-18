package com.shyrski.profit.tracker.config;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.shyrski.profit.tracker.exception.ExceptionDetails;
import com.shyrski.profit.tracker.exception.ServerException;
import com.shyrski.profit.tracker.exception.message.ApiSubMessage;
import com.shyrski.profit.tracker.exception.message.ValidationFailedMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ExceptionDetails> handleException(ServerException exception) {
        return new ResponseEntity<>(exception.getExceptionDetails(),
                exception.getExceptionDetails().getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDetails> handleException(RuntimeException exception) {
        ExceptionDetails exceptionDetails = buildDetails(exception, FORBIDDEN);
        return new ResponseEntity<>(exceptionDetails, FORBIDDEN);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionDetails> handleException(HttpMessageNotReadableException exception) {
        ExceptionDetails exceptionDetails = buildDetails(exception, BAD_REQUEST);
        return new ResponseEntity<>(exceptionDetails, BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionDetails> handleException(MissingServletRequestParameterException exception) {
        List<ApiSubMessage> messages = new ArrayList<>();

        ValidationFailedMessage message = ValidationFailedMessage.builder()
                .field(exception.getParameterName())
                .rejectedValue(null)
                .message(exception.getMessage())
                .build();

        messages.add(message);

        ExceptionDetails exceptionDetails = ExceptionDetails.validationFailed(messages);
        return new ResponseEntity<>(exceptionDetails, BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionDetails> handleException(ConstraintViolationException exception) {
        List<ApiSubMessage> messages = new ArrayList<>();

        exception.getConstraintViolations().forEach(constraintViolation -> {
            PathImpl path = (PathImpl) constraintViolation.getPropertyPath();
            String field = path.getLeafNode().asString();

            ValidationFailedMessage message = ValidationFailedMessage.builder()
                    .field(field)
                    .rejectedValue(constraintViolation.getInvalidValue())
                    .message(path.getLeafNode().asString() + " " + constraintViolation.getMessage())
                    .build();

            messages.add(message);
        });

        ExceptionDetails exceptionDetails = ExceptionDetails.validationFailed(messages);
        return new ResponseEntity<>(exceptionDetails, BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionDetails> handleException(BindException exception) {
        String fieldName = Optional.ofNullable(exception.getFieldError())
                .map(FieldError::getField)
                .orElse(null);

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
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDetails> handleException(Exception exception) {
        log.error("Internal server error", exception);

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .status(INTERNAL_SERVER_ERROR)
                .message(exception.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(exceptionDetails, INTERNAL_SERVER_ERROR);
    }
}
