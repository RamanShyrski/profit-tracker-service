package com.shyrski.profit.tracker.model.dto.nft;

import com.shyrski.profit.tracker.model.dto.FileDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NftDto {
    private Long nftId;
    private String name;
    private FileDto image;
    private String floorPrice;
    private String tokenId;
    private Long idInMarketplace;
    private String description;
    private String contractAddress;
}
