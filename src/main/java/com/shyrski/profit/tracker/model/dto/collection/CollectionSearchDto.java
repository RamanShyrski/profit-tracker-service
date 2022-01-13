package com.shyrski.profit.tracker.model.dto.collection;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionSearchDto {
    @NotNull
    @Parameter(description = "Portfolio unique identifier", example = "552354", required = true)
    private Long portfolioId;
    @Parameter(description = "Collection name", example = "My collection")
    private String name;
    @Parameter(description = "Network name", example = "Ethereum")
    private String network;
}
