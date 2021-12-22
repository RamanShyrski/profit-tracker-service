package com.shyrski.profit.tracker.service;

import java.util.List;

import com.shyrski.profit.tracker.model.dto.NftDto;

public interface NftService {

    List<NftDto> findNftsInCollection(Long collectionId);
}
