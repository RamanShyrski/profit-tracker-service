package com.shyrski.profit.tracker.mapper;

import static com.shyrski.profit.tracker.exception.message.ExceptionMessages.INVALID_MARKETPLACE_NAME;
import static com.shyrski.profit.tracker.exception.message.ExceptionMessages.INVALID_NETWORK_NAME;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shyrski.profit.tracker.client.OpenSeaClient;
import com.shyrski.profit.tracker.exception.ExceptionDetails;
import com.shyrski.profit.tracker.exception.ServerException;
import com.shyrski.profit.tracker.model.db.Collection;
import com.shyrski.profit.tracker.model.db.CollectionMarketplace;
import com.shyrski.profit.tracker.model.db.CollectionType;
import com.shyrski.profit.tracker.model.db.LogEntity;
import com.shyrski.profit.tracker.model.db.Network;
import com.shyrski.profit.tracker.model.db.Nft;
import com.shyrski.profit.tracker.model.dto.collection.CollectionDto;
import com.shyrski.profit.tracker.model.dto.opensea.OpenSeaCollectionDto;
import com.shyrski.profit.tracker.model.dto.opensea.OpenSeaCollectionStatisticsResponse;
import com.shyrski.profit.tracker.repository.CollectionMarketplaceRepository;
import com.shyrski.profit.tracker.repository.NetworkRepository;
import com.shyrski.profit.tracker.service.S3BucketService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CollectionMapper {

    private final S3BucketService s3BucketService;
    private final CollectionMarketplaceRepository collectionMarketplaceRepository;
    private final NetworkRepository networkRepository;
    private final NftMapper customNftMapper;
    private final OpenSeaClient openSeaClient;

    @Value("${aws.s3.collection-image-bucket-name}")
    private String collectionsImagesBucketName;

    public List<CollectionDto> toDtoListFromOpensea(List<OpenSeaCollectionDto> openSeaCollectionDto) {
        if (isEmpty(openSeaCollectionDto)) {
            return null;
        }

        List<CollectionDto> resultList = new ArrayList<>();

        openSeaCollectionDto.forEach(openSeaCollection -> {
            CollectionDto collection = new CollectionDto();
            collection.setName(openSeaCollection.getName());
            collection.setImage(openSeaCollection.getImageUrl());
            collection.setMarketplace("Opensea");
            collection.setIdInMarketplace(openSeaCollection.getSlug());
            collection.setItems(openSeaCollection.getItems());
            collection.setFloorPrice(mapPriceField(openSeaCollection.getStatistics().getFloorPrice(), "ETH"));
            collection.setTradingVolume(mapPriceField(openSeaCollection.getStatistics().getTradingVolume(), "ETH"));
            collection.setNetwork(null);
            collection.setType(CollectionType.PUBLIC);

            resultList.add(collection);
        });

        return resultList;
    }

    private String mapPriceField(BigDecimal value, String currency) {
        if (isEmpty(value)) {
            return null;
        }
        return value.setScale(2, RoundingMode.CEILING) + " " + currency;
    }

    public List<Collection> toDatabaseList(List<CollectionDto> collectionDtos) {
        if (isEmpty(collectionDtos)) {
            return null;
        }
        List<Collection> resultList = new ArrayList<>();

        collectionDtos.forEach(dto -> {
            Collection collection = new Collection();

            collection.setCollectionId(dto.getCollectionId());
            if (dto.getCollectionId() == null) {
                collection.setImageKey(s3BucketService.uploadImage(dto.getImage(), collectionsImagesBucketName));
            } else {
                collection.setImageKey(dto.getImage());
            }
            collection.setName(dto.getName());
            collection.setType(dto.getType());
            collection.setIdInMarketplace(dto.getIdInMarketplace());
            collection.setUrl(dto.getUrl());
            collection.setCollectionMarketplace(retrieveMarketplace(dto.getMarketplace()));
            collection.setNetwork(retrieveNetwork(dto.getNetwork()));
            collection.setNfts(customNftMapper.toDatabaseList(dto.getNfts()));
            collection.setLogEntity(new LogEntity());

            resultList.add(collection);
        });

        return resultList;
    }

    private CollectionMarketplace retrieveMarketplace(String marketplaceName) {
        if (isEmpty(marketplaceName)) {
            return null;
        }
        return collectionMarketplaceRepository.findByName(marketplaceName)
                .orElseThrow(() -> new ServerException(ExceptionDetails.badRequest(INVALID_MARKETPLACE_NAME)));

    }

    private Network retrieveNetwork(String networkName) {
        if (isEmpty(networkName)) {
            return null;
        }
        return networkRepository.findByName(networkName)
                .orElseThrow(() -> new ServerException(ExceptionDetails.badRequest(INVALID_NETWORK_NAME)));
    }

    public List<CollectionDto> toDtoList(List<Collection> collections) {
        if (isEmpty(collections)) {
            return null;
        }

        List<CollectionDto> resultList = new ArrayList<>();

        collections.forEach(collection -> {
            CollectionDto dto = new CollectionDto();

            dto.setCollectionId(collection.getCollectionId());
            dto.setName(collection.getName());
            dto.setImage(collection.getImageKey());
            dto.setItems(calculateItems(collection.getNfts()));
            dto.setUrl(collection.getUrl());
            dto.setNetwork(Optional.ofNullable(collection.getNetwork())
                    .map(Network::getName)
                    .orElse(null));

            dto.setIdInMarketplace(collection.getIdInMarketplace());

            dto.setMarketplace(Optional.ofNullable(collection.getCollectionMarketplace())
                    .map(CollectionMarketplace::getName)
                    .orElse(null));

            dto.setTradingVolume(retrieveTradingVolume(collection));
            dto.setFloorPrice(retrieveFloorPrice(collection));

            dto.setType(collection.getType());
            dto.setNfts(customNftMapper.toDtoList(collection.getNfts()));

            resultList.add(dto);
        });

        return resultList;
    }

    private String retrieveTradingVolume(Collection collection) {
        if (collection.getType().equals(CollectionType.PUBLIC)) {
            if (collection.getCollectionMarketplace().getName().equals("Opensea")) {

                OpenSeaCollectionStatisticsResponse response =
                        openSeaClient.retrieveCollectionStatistics(collection.getIdInMarketplace());
                return mapPriceField(response.getStatistics().getTradingVolume(), "ETH");
            }
        }
        return null;
    }

    private String retrieveFloorPrice(Collection collection) {
        if (collection.getType().equals(CollectionType.PUBLIC)) {
            if (collection.getCollectionMarketplace().getName().equals("Opensea")) {

                OpenSeaCollectionStatisticsResponse response =
                        openSeaClient.retrieveCollectionStatistics(collection.getIdInMarketplace());
                return mapPriceField(response.getStatistics().getFloorPrice(), "ETH");
            }
        }
        return null;
    }

    protected Long calculateItems(List<Nft> nfts) {
        if (isEmpty(nfts)) {
            return 0L;
        }
        return (long) nfts.size();
    }
}
