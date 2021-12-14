package com.shyrski.profit.tracker.model.db;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Nft {
    @Id
    private Long nftId;
    private String name;
    private String imageKey;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COLLECTION_ID")
    private Collection collection;
    @Embedded
    private LogEntity logEntity;
}
