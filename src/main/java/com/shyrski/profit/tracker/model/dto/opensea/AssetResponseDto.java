package com.shyrski.profit.tracker.model.dto.opensea;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetResponseDto {

    private List<NftDto> assets;
}
