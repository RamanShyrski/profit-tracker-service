package com.shyrski.profit.tracker.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PortfolioDto {
    @ApiModelProperty(position = 1, example = "3257512054",
            value = "Portfolio unique identifier")
    private Long portfolioId;
    @ApiModelProperty(position = 2, example = "My best portfolio",
            value = "Portfolio name")
    private String name;
}
