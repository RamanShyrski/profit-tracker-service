package com.shyrski.profit.tracker.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shyrski.profit.tracker.mapper.CollectionMapper;
import com.shyrski.profit.tracker.model.db.Collection;
import com.shyrski.profit.tracker.model.dto.CollectionDto;
import com.shyrski.profit.tracker.repository.CollectionRepository;
import com.shyrski.profit.tracker.service.CollectionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;
    private final CollectionMapper collectionMapper;

    @Override
    public List<CollectionDto> findAllCollections(Long portfolioId) {
        List<Collection> collections = collectionRepository.findAllByPortfolioId(portfolioId);

        return collectionMapper.toDtoList(collections);
    }
}
