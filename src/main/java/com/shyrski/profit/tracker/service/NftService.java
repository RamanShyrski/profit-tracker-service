package com.shyrski.profit.tracker.service;

import java.util.List;

import com.shyrski.profit.tracker.model.dto.NftDto;
import com.shyrski.profit.tracker.model.dto.NftSearchDto;

public interface NftService {

    List<NftDto> findNftsInCollection(NftSearchDto nftSearchDto);
}
