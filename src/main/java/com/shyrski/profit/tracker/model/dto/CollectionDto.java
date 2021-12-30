package com.shyrski.profit.tracker.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionDto {
    @ApiModelProperty(position = 1, example = "93752354",
            value = "Collection unique identifier")
    private Long collectionId;
    @ApiModelProperty(position = 2, example = "Base64 encoded image",
            value = "Base64 encoded image")
    private String image;
    @ApiModelProperty(position = 3, example = "My collection",
            value = "Collection name")
    private String name;
    @ApiModelProperty(position = 4, example = "4",
            value = "Number of NFTs in collection")
    private Long items;
    @ApiModelProperty(position = 5, example = "Ethereum",
            value = "Collection network name")
    private String network;
}
