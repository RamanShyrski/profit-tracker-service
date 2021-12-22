package com.shyrski.profit.tracker.model.db;

import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Collection {
    @Id
    private Long collectionId;
    private String name;
    private String imageKey;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NETWORK_ID")
    private Network network;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PORTFOLIO_ID")
    private Portfolio portfolio;
    @OneToMany(mappedBy = "collection")
    private List<Nft> nfts;
    @Embedded
    private LogEntity logEntity;
}
