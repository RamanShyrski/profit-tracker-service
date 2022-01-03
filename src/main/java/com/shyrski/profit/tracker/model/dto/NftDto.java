package com.shyrski.profit.tracker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NftDto {
    private String nftId;
    private String name;
    private String image;
    private String price;
}
