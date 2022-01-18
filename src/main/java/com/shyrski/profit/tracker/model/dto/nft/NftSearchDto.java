package com.shyrski.profit.tracker.model.dto.nft;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NftSearchDto {
    @NotNull
    @Parameter(description = "Collection unique identifier", example = "203813631")
    private Long collectionId;
    @Parameter(description = "Collection name", example = "My collection")
    private String name;
}
