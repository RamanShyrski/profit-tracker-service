package com.shyrski.profit.tracker.util;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;

import com.shyrski.profit.tracker.exception.ExceptionDetails;
import com.shyrski.profit.tracker.exception.ServerException;
import com.shyrski.profit.tracker.exception.message.ApiSubMessage;
import com.shyrski.profit.tracker.exception.message.ValidationFailedMessage;

public class ValidatorUtil {

    public static void validateFieldNotEmpty(Object fieldValue, String fieldName, String objectName) {
        if (isEmpty(fieldValue)) {
            throw new ServerException(ExceptionDetails
                    .validationFailed(createValidationMessage(fieldName + " should not be empty",
                            fieldValue, fieldName, objectName)));
        }
    }

    public static List<ApiSubMessage> createValidationMessage(String message, Object fieldValue, String fieldName, String objectName) {
        List<ApiSubMessage> messages = new ArrayList<>();

        messages.add(ValidationFailedMessage.builder()
                .field(fieldName)
                .object(objectName)
                .rejectedValue(fieldValue)
                .message(message)
                .build());

        return messages;
    }
}
