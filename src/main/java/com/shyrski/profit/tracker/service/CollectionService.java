package com.shyrski.profit.tracker.service;

import java.util.List;

import com.shyrski.profit.tracker.model.dto.collection.CollectionDto;
import com.shyrski.profit.tracker.model.dto.collection.CollectionSearchDto;

public interface CollectionService {

    List<CollectionDto> findAllCollectionsBySearchCriteria(CollectionSearchDto collectionSearchDto);

    List<CollectionDto> getCollectionsFromOpenSeaAddress(String openSeaAddress);

    void createCollections(Long portfolioId, List<CollectionDto> collectionDtos);
}
