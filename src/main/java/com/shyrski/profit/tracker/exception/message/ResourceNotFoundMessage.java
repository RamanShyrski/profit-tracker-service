package com.shyrski.profit.tracker.exception.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ResourceNotFoundMessage implements ApiSubMessage {
    private String object;
    private Long requestedId;
    private String message;
}
