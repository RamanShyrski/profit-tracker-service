package com.shyrski.profit.tracker.service;

import java.util.List;

import com.shyrski.profit.tracker.model.dto.CollectionDto;

public interface CollectionService {

    List<CollectionDto> findAllCollections(Long portfolioId);
}
