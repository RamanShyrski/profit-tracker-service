package com.shyrski.profit.tracker.model.dto.nft;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NftDto {
    private Long nftId;
    private String name;
    private String image;
    private String floorPrice;
}
