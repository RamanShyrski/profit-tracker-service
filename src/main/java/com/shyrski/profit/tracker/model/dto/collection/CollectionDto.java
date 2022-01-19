package com.shyrski.profit.tracker.model.dto.collection;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.shyrski.profit.tracker.model.db.CollectionType;
import com.shyrski.profit.tracker.model.dto.nft.NftDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionDto {
    @Schema(description = "Collection unique identifier in our system", example = "93752354")
    private Long collectionId;
    @NotNull
    @Schema(description = "Base64 encoded image or image url", example = "Base64 encoded image or image url")
    private String image;
    @NotBlank
    @Schema(description = "Collection name", example = "My collection", required = true)
    private String name;
    @Schema(description = "Number of NFTs in collection", example = "4", required = true)
    private Long items;
    @Schema(description = "Collection network name", example = "Ethereum")
    private String network;
    @Schema(description = "If collection is public, id in the marketplace", example = "my-collection-slug")
    private String idInMarketplace;
    @Schema(description = "If collection is public, it's marketplace name", example = "opensea")
    private String marketplace;
    @Schema(description = "Total trading volume from marketplace")
    private String tradingVolume;
    @Schema(description = "Floor price from marketplace")
    private String floorPrice;
    @NotNull
    @Schema(description = "CUSTOM or PUBLIC", example = "PUBLIC", required = true)
    private CollectionType type;
    @Schema(description = "List of NFTS in this collection")
    private List<@Valid NftDto> nfts;
}
