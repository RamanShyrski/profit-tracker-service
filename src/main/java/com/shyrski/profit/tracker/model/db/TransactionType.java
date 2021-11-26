package com.shyrski.profit.tracker.model.db;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "\"transaction_type\"")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TransactionType {
    @Id
    private Long transactionTypeId;
    private String type;
}
