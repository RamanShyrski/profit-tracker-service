package com.shyrski.profit.tracker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CollectionDto {
    private Long collectionId;
    private String name;
    private Long items;
}
