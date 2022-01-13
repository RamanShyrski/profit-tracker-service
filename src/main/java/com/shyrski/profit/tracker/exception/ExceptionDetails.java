package com.shyrski.profit.tracker.exception;


import static com.shyrski.profit.tracker.exception.message.ExceptionMessages.RESOURCE_NOT_FOUND;
import static com.shyrski.profit.tracker.exception.message.ExceptionMessages.UNAUTHORIZED;
import static com.shyrski.profit.tracker.exception.message.ExceptionMessages.VALIDATION_FAILED;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shyrski.profit.tracker.exception.message.ApiSubMessage;
import com.shyrski.profit.tracker.exception.message.ResourceNotFoundMessage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionDetails {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubMessage> subMessages;

    public static ExceptionDetails internalServerError(String message) {
        return ExceptionDetails.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now())
                .message(message)
                .build();
    }

    public static ExceptionDetails internalServerError(String message, Exception e) {
        return ExceptionDetails.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now())
                .message(message)
                .debugMessage(e.getMessage())
                .build();
    }

    public static ExceptionDetails internalServerError(String message, String debugMessage) {
        return ExceptionDetails.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now())
                .message(message)
                .debugMessage(debugMessage)
                .build();
    }

    public static ExceptionDetails validationFailed(List<ApiSubMessage> subMessages) {
        return ExceptionDetails.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(VALIDATION_FAILED)
                .timestamp(LocalDateTime.now())
                .subMessages(subMessages)
                .build();
    }

    public static ExceptionDetails resourceNotFound(Resource resource, Long requestedId) {
        List<ApiSubMessage> messages = new ArrayList<>();

        messages.add(ResourceNotFoundMessage.builder()
                .object(resource.getValue())
                .requestedId(requestedId)
                .message(String.format("%s with id %d not found", resource.getValue(), requestedId))
                .build());

        return ExceptionDetails.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(RESOURCE_NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .subMessages(messages)
                .build();
    }

    public static ExceptionDetails resourceNotFound(Resource resource) {
        List<ApiSubMessage> messages = new ArrayList<>();

        messages.add(ResourceNotFoundMessage.builder()
                .object(resource.getValue())
                .requestedId(null)
                .message(String.format("Requested %s not found", resource.getValue()))
                .build());

        return ExceptionDetails.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(RESOURCE_NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .subMessages(messages)
                .build();
    }

    public static ExceptionDetails unauthorized() {
        return ExceptionDetails.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(UNAUTHORIZED)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
