package com.shyrski.profit.tracker.exception;

import lombok.Getter;

@Getter
public class ServerException extends RuntimeException {

    private final ExceptionDetails exceptionDetails;

    public ServerException(ExceptionDetails exceptionDetails) {
        super(exceptionDetails.getMessage());
        this.exceptionDetails = exceptionDetails;
    }
}
