package com.shyrski.profit.tracker.model.db;

import java.time.Instant;
import javax.persistence.Embeddable;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LogEntity {
    @CreatedDate
    private Instant creationDate;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private Instant lastUpdateDate;
    @LastModifiedBy
    private String lastUpdatedBy;
}
