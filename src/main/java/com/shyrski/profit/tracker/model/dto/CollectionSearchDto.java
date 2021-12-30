package com.shyrski.profit.tracker.model.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionSearchDto {
    @NotNull
    @ApiParam(value = "Portfolio unique identifier", required = true)
    private Long portfolioId;
    @ApiParam("Collection name")
    private String name;
    @ApiParam("Collection network name")
    private String network;
}
