package com.shyrski.profit.tracker.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioDto {
    @Schema(description = "Portfolio unique identifier", example = "3257512054", required = true)
    private Long portfolioId;
    @Schema(description = "Portfolio name", example = "My best portfolio", required = true)
    private String name;
}
