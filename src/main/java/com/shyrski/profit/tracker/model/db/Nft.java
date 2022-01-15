package com.shyrski.profit.tracker.model.db;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Nft {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nft_generator")
    @SequenceGenerator(name = "nft_generator", sequenceName = "nft_sequence")
    private Long nftId;
    private String name;
    private String imageKey;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COLLECTION_ID")
    private Collection collection;
    private String tokenId;
    private String description;
    private String idInMarketplace;
    private String contractAddress;
    @Embedded
    private LogEntity logEntity;
}
