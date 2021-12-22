package com.shyrski.profit.tracker.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class NftDto {

    private String name;
    private String image;
    private String price;

}
