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
public class CollectionMarketplace {
    @Id
    private Long collectionMarketplaceId;
    private String name;
    @Embedded
    private LogEntity logEntity;
}
