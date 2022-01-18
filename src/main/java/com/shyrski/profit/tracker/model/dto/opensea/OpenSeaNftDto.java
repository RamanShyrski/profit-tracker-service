package com.shyrski.profit.tracker.model.dto.opensea;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenSeaNftDto {
    private Long id;
    private String name;
    @JsonProperty("token_id")
    private String tokenId;
    @JsonProperty("image_url")
    private String imageUrl;
    private String description;
    @JsonProperty("asset_contract")
    private OpenSeaAssetContractDto assetContract;
}
