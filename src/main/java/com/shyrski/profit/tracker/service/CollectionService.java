package com.shyrski.profit.tracker.service;

import java.util.List;

import com.shyrski.profit.tracker.model.dto.CollectionDto;
import com.shyrski.profit.tracker.model.dto.CollectionSearchDto;

public interface CollectionService {

    List<CollectionDto> findAllCollectionsBySearchCriteria(CollectionSearchDto collectionSearchDto);
}
