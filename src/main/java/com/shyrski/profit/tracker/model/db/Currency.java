package com.shyrski.profit.tracker.model.db;

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
public class Currency {
    @Id
    private Long currencyId;
    private String code;
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_type_id")
    private CurrencyType currencyType;
}
