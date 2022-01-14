package com.shyrski.profit.tracker.service.impl;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shyrski.profit.tracker.client.OpenSeaClient;
import com.shyrski.profit.tracker.exception.ExceptionDetails;
import com.shyrski.profit.tracker.exception.Resource;
import com.shyrski.profit.tracker.exception.ServerException;
import com.shyrski.profit.tracker.mapper.CollectionMapper;
import com.shyrski.profit.tracker.model.db.Collection;
import com.shyrski.profit.tracker.model.db.CollectionMarketplace;
import com.shyrski.profit.tracker.model.db.CollectionType;
import com.shyrski.profit.tracker.model.db.LogEntity;
import com.shyrski.profit.tracker.model.db.Network;
import com.shyrski.profit.tracker.model.db.Portfolio;
import com.shyrski.profit.tracker.model.dto.collection.CollectionDto;
import com.shyrski.profit.tracker.model.dto.collection.CollectionSearchDto;
import com.shyrski.profit.tracker.model.dto.opensea.OpenSeaCollectionDto;
import com.shyrski.profit.tracker.repository.CollectionMarketplaceRepository;
import com.shyrski.profit.tracker.repository.CollectionRepository;
import com.shyrski.profit.tracker.repository.NetworkRepository;
import com.shyrski.profit.tracker.repository.PortfolioRepository;
import com.shyrski.profit.tracker.repository.specification.CollectionSpecification;
import com.shyrski.profit.tracker.service.CollectionService;
import com.shyrski.profit.tracker.service.S3BucketService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;
    private final CollectionMapper collectionMapper;
    private final OpenSeaClient openSeaClient;
    private final S3BucketService s3BucketService;
    private final CollectionMarketplaceRepository collectionMarketplaceRepository;
    private final NetworkRepository networkRepository;
    private final PortfolioRepository portfolioRepository;

    @Value("${aws.s3.collection-image-bucket-name}")
    private String collectionsImagesBucketName;

    @Override
    public List<CollectionDto> findAllCollectionsBySearchCriteria(CollectionSearchDto collectionSearchDto) {
        CollectionSpecification specification = new CollectionSpecification(collectionSearchDto);
        List<Collection> collections = collectionRepository.findAll(specification);

        return collectionMapper.toDtoList(collections);
    }

    @Override
    public List<CollectionDto> getCollectionsFromOpenSeaAddress(String openSeaAddress) {
        List<OpenSeaCollectionDto> collections =
                openSeaClient.findCollections(openSeaAddress, 300, 0);

        return collectionMapper.toDtoListFromOpenSeaDtoList(collections);
    }

    @Override
    @Transactional
    public void createCollections(Long portfolioId, List<CollectionDto> collectionDtos) {
        List<Collection> listToCreate = new ArrayList<>();

        collectionDtos.forEach(collectionDto -> {
            Collection collection = new Collection();

            if (isNotEmpty(collectionDto.getImage())) {
                collection.setImageKey(s3BucketService.uploadImage(collectionDto.getImage(), collectionsImagesBucketName));
            }

            collection.setName(collectionDto.getName());
            collection.setType(CollectionType.valueOf(collectionDto.getType()));

            collection.setIdInMarketplace(collectionDto.getIdInMarketplace());

            if (isNotEmpty(collectionDto.getMarketplace())) {
                CollectionMarketplace collectionMarketplace = collectionMarketplaceRepository.findByName(collectionDto.getMarketplace())
                        .orElseThrow(() -> new ServerException(ExceptionDetails.resourceNotFound(Resource.MARKETPLACE)));
                collection.setCollectionMarketplace(collectionMarketplace);
            }

            if (isNotEmpty(collectionDto.getNetwork())) {
                Network network = networkRepository.findByName(collectionDto.getNetwork())
                        .orElseThrow(() -> new ServerException(ExceptionDetails.resourceNotFound(Resource.NETWORK)));
                collection.setNetwork(network);
            }

            Portfolio portfolio = portfolioRepository.findById(portfolioId)
                    .orElseThrow(() -> new ServerException(ExceptionDetails.resourceNotFound(Resource.PORTFOLIO, portfolioId)));
            collection.setPortfolio(portfolio);

            collection.setLogEntity(new LogEntity());

            listToCreate.add(collection);
        });

        collectionRepository.saveAll(listToCreate);
    }
}
