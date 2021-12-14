package com.shyrski.profit.tracker.model.db;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {
    @Id
    private Long portfolioId;
    private String name;
    private String userId;
    @Embedded
    private LogEntity logEntity;
}
