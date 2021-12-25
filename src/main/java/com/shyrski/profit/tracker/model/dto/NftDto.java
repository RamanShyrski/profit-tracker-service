package com.shyrski.profit.tracker.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class NftDto {
    @ApiModelProperty(position = 1, example = "4451237845",
            value = "NFT unique identifier")
    private String nftId;
    @ApiModelProperty(position = 2, example = "My favourite NFT",
            value = "NFT name")
    private String name;
    @ApiModelProperty(position = 3, example = "Base64 encoded image",
            value = "Base64 encoded NFT image")
    private String image;
    @ApiModelProperty(position = 4, example = "400$",
            value = "Current floor price")
    private String price;
}
