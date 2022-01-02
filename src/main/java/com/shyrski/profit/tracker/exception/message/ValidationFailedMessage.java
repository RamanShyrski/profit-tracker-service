package com.shyrski.profit.tracker.exception.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ValidationFailedMessage implements ApiSubMessage {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;
}
