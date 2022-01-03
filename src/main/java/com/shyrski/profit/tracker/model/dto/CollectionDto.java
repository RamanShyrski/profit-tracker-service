package com.shyrski.profit.tracker.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionDto {
    @Schema(description = "Collection unique identifier", example = "93752354", required = true)
    private Long collectionId;
    @Schema(description = "Base64 encoded image", example = "Base64 encoded image", required = true)
    private String image;
    @Schema(description = "Collection name", example = "My collection", required = true)
    private String name;
    @Schema(description = "Number of NFTs in collection", example = "4", required = true)
    private Long items;
    @Schema(description = "Collection network name", example = "Ethereum", required = true)
    private String network;
}
