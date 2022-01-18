package com.shyrski.profit.tracker.service;

import java.util.List;

import com.shyrski.profit.tracker.model.dto.nft.NftDto;
import com.shyrski.profit.tracker.model.dto.nft.NftSearchDto;

public interface NftService {

    List<NftDto> findNftsInCollection(NftSearchDto nftSearchDto);
}
