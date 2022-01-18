package com.shyrski.profit.tracker.model.dto.nft;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.shyrski.profit.tracker.model.db.NftType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NftDto {
    private Long nftId;
    @NotEmpty
    private String name;
    private String image;
    @NotNull
    private NftType type;
    private String floorPrice;
    private String tokenId;
    private Long idInMarketplace;
    private String description;
    private String contractAddress;
}
