package com.shyrski.profit.tracker.model.db;

import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "collection_generator")
    @SequenceGenerator(name = "collection_generator", sequenceName = "collections_sequence")
    private Long collectionId;
    private String name;
    private String imageKey;
    @Enumerated(EnumType.STRING)
    private CollectionType type;
    private String idInMarketplace;
    @ManyToOne
    @JoinColumn(name = "MARKETPLACE_ID")
    private CollectionMarketplace collectionMarketplace;
    @ManyToOne
    @JoinColumn(name = "NETWORK_ID")
    private Network network;
    @ManyToOne
    @JoinColumn(name = "PORTFOLIO_ID")
    private Portfolio portfolio;
    @OneToMany(mappedBy = "collection")
    private List<Nft> nfts;
    @Embedded
    private LogEntity logEntity;
}
