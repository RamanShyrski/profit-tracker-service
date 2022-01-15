package com.shyrski.profit.tracker.service.impl;

import static com.shyrski.profit.tracker.exception.message.ExceptionMessages.INVALID_PORTFOLIO_ID;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shyrski.profit.tracker.client.OpenSeaClient;
import com.shyrski.profit.tracker.exception.ExceptionDetails;
import com.shyrski.profit.tracker.exception.Resource;
import com.shyrski.profit.tracker.exception.ServerException;
import com.shyrski.profit.tracker.mapper.CollectionMapper;
import com.shyrski.profit.tracker.mapper.NftMapper;
import com.shyrski.profit.tracker.model.db.Collection;
import com.shyrski.profit.tracker.model.db.Nft;
import com.shyrski.profit.tracker.model.db.Portfolio;
import com.shyrski.profit.tracker.model.dto.collection.CollectionDto;
import com.shyrski.profit.tracker.model.dto.collection.CollectionSearchDto;
import com.shyrski.profit.tracker.model.dto.nft.NftDto;
import com.shyrski.profit.tracker.model.dto.opensea.OpenSeaAssetsResponse;
import com.shyrski.profit.tracker.model.dto.opensea.OpenSeaCollectionDto;
import com.shyrski.profit.tracker.repository.CollectionRepository;
import com.shyrski.profit.tracker.repository.NftRepository;
import com.shyrski.profit.tracker.repository.PortfolioRepository;
import com.shyrski.profit.tracker.repository.specification.CollectionSpecification;
import com.shyrski.profit.tracker.service.CollectionService;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {
    private final CollectionRepository collectionRepository;
    private final CollectionMapper collectionMapper;
    private final NftMapper nftMapper;
    private final OpenSeaClient openSeaClient;
    private final PortfolioRepository portfolioRepository;
    private final NftRepository nftRepository;

    @Override
    public List<CollectionDto> findAllCollectionsBySearchCriteria(CollectionSearchDto collectionSearchDto) {
        CollectionSpecification specification = new CollectionSpecification(collectionSearchDto);
        List<Collection> collections = collectionRepository.findAll(specification);

        return collectionMapper.toDtoList(collections);
    }

    @Override
    public List<CollectionDto> getCollectionsFromOpenSeaAddress(String openSeaAddress) {
        List<OpenSeaCollectionDto> collections;
        try {
            collections = openSeaClient.findCollections(openSeaAddress, 300, 0);
        } catch (FeignException.BadRequest badRequest) {
            throw new ServerException(ExceptionDetails.resourceNotFound(Resource.OPENSEA_ADDRESS, openSeaAddress));
        }

        List<CollectionDto> collectionDtos = collectionMapper.toDtoListFromOpenSeaDtoList(collections);

        collectionDtos.forEach(collectionDto -> {
            OpenSeaAssetsResponse collectionNftsDtos =
                    openSeaClient.findNftsInCollection(openSeaAddress, collectionDto.getIdInMarketplace(), 50, 0);
            List<NftDto> nftDtos = nftMapper.toDtoListFromOpenSeaDtoList(collectionNftsDtos.getAssets());

            collectionDto.setNfts(nftDtos);
        });

        return collectionDtos;
    }

    @Override
    @Transactional
    public void createCollections(Long portfolioId, List<CollectionDto> collectionDtos) {
        List<Nft> nftsToCreate = new ArrayList<>();
        List<Collection> collectionsToCreate = collectionMapper.fromDtoList(collectionDtos);

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ServerException(ExceptionDetails.badRequest(INVALID_PORTFOLIO_ID)));

        collectionsToCreate.forEach(collection -> {
            collection.setPortfolio(portfolio);

            if (isNotEmpty(collection.getNfts())) {
                collection.getNfts().forEach(nft -> nft.setCollection(collection));
                nftsToCreate.addAll(collection.getNfts());
            }
        });

        collectionRepository.saveAllAndFlush(collectionsToCreate);
        if (!nftsToCreate.isEmpty()) {
            nftRepository.saveAll(nftsToCreate);
        }
    }
}
