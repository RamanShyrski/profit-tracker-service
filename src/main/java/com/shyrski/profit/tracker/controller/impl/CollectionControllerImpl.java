package com.shyrski.profit.tracker.controller.impl;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.shyrski.profit.tracker.controller.CollectionController;
import com.shyrski.profit.tracker.model.dto.collection.CollectionDto;
import com.shyrski.profit.tracker.model.dto.collection.CollectionSearchDto;
import com.shyrski.profit.tracker.service.CollectionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CollectionControllerImpl implements CollectionController {

    private final CollectionService collectionService;

    @Override
    public List<CollectionDto> findAllCollectionsBySearchCriteria(CollectionSearchDto collectionSearchDto) {
        return collectionService.findAllCollectionsBySearchCriteria(collectionSearchDto);
    }

    @Override
    public List<CollectionDto> getCollectionsFromOpenSeaAddress(String address) {
        return collectionService.getCollectionsFromOpenSeaAddress(address);
    }

    @Override
    public void createCollections(Long portfolioId, List<CollectionDto> collectionDtos) {
        collectionService.createCollections(portfolioId, collectionDtos);
    }
}
