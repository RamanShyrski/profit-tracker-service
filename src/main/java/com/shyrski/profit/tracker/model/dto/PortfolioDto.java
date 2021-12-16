package com.shyrski.profit.tracker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PortfolioDto {
    private Long portfolioId;
    private String name;
}
