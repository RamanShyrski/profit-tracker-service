package com.shyrski.profit.tracker.model.dto.opensea;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionDto {

    private String name;
    @JsonProperty("image_url")
    private String imageUrl;

}
