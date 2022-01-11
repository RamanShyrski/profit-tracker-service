package com.shyrski.profit.tracker.model.dto.opensea;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NftDto {

    private String name;
    private CollectionDto collection;

}
