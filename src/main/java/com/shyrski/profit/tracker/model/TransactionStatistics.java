package com.shyrski.profit.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionStatistics {
    private Double invested;
    private Double currentCost;
    private Double costChange;
    private Double percentageChange;
}
