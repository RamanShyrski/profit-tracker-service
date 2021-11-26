package com.shyrski.profit.tracker.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    @JsonProperty(value = "market_data")
    private MarketData marketData;
}
