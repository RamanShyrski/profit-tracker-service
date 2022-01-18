package com.shyrski.profit.tracker.mapper;

import static com.shyrski.profit.tracker.exception.message.ExceptionMessages.INVALID_COLLECTION_TYPE;
import static com.shyrski.profit.tracker.exception.message.ExceptionMessages.INVALID_MARKETPLACE_NAME;
import static com.shyrski.profit.tracker.exception.message.ExceptionMessages.INVALID_NETWORK_NAME;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.shyrski.profit.tracker.exception.ExceptionDetails;
import com.shyrski.profit.tracker.exception.ServerException;
import com.shyrski.profit.tracker.model.db.Collection;
import com.shyrski.profit.tracker.model.db.CollectionMarketplace;
import com.shyrski.profit.tracker.model.db.CollectionType;
import com.shyrski.profit.tracker.model.db.LogEntity;
import com.shyrski.profit.tracker.model.db.Network;
import com.shyrski.profit.tracker.model.db.Nft;
import com.shyrski.profit.tracker.model.dto.collection.CollectionDto;
import com.shyrski.profit.tracker.model.dto.nft.NftDto;
import com.shyrski.profit.tracker.model.dto.opensea.OpenSeaCollectionDto;
import com.shyrski.profit.tracker.repository.CollectionMarketplaceRepository;
import com.shyrski.profit.tracker.repository.NetworkRepository;
import com.shyrski.profit.tracker.service.S3BucketService;
import com.shyrski.profit.tracker.util.FileUtil;

@Mapper(componentModel = "spring")
public abstract class CollectionMapper {

    @Autowired
    private S3BucketService s3BucketService;
    @Autowired
    private CollectionMarketplaceRepository collectionMarketplaceRepository;
    @Autowired
    private NetworkRepository networkRepository;
    @Autowired
    private NftMapper nftMapper;

    @Value("${aws.s3.collection-image-bucket-name}")
    private String collectionsImagesBucketName;

    @Mapping(target = "items", source = "nfts", qualifiedByName = "generateItems")
    @Mapping(target = "image", source = "imageKey")
    @Mapping(target = "network", source = "network.name")
    @Mapping(target = "marketplace", source = "collectionMarketplace.name")
    @Mapping(target = "nfts", source = "nfts", qualifiedByName = "mapNftListToDto")
    public abstract CollectionDto toDto(Collection collection);

    public abstract List<CollectionDto> toDtoList(List<Collection> collection);

    @Mapping(target = "idInMarketplace", source = "slug")
    @Mapping(target = "marketplace", constant = "opensea")
    @Mapping(target = "type", constant = "PUBLIC")
    @Mapping(target = "image", source = "imageUrl")
    public abstract CollectionDto toDto(OpenSeaCollectionDto openSeaCollectionDto);

    @Mapping(target = "imageKey", source = "image", qualifiedByName = "uploadImageToS3")
    @Mapping(target = "collectionMarketplace", source = "marketplace", qualifiedByName = "retrieveMarketplace")
    @Mapping(target = "network", source = "network", qualifiedByName = "retrieveNetwork")
    @Mapping(target = "nfts", source = "nfts", qualifiedByName = "mapNftListFromDto")
    @Mapping(target = "type", source = "type", qualifiedByName = "retrieveCollectionType")
    public abstract Collection fromDto(CollectionDto collectionDto);

    public abstract List<Collection> fromDtoList(List<CollectionDto> collectionDto);

    public abstract List<CollectionDto> toDtoListFromOpenSeaDtoList(List<OpenSeaCollectionDto> openSeaCollectionDto);

    @Named("generateItems")
    protected Long generateItems(List<Nft> nfts) {
        if (isEmpty(nfts)) {
            return 0L;
        }
        return (long) nfts.size();
    }

    @Named("uploadImageToS3")
    protected String uploadImageToS3(String based64Image) {
        return s3BucketService.uploadImage(based64Image, collectionsImagesBucketName);
    }

    @Named("retrieveMarketplace")
    protected CollectionMarketplace retrieveMarketplace(String marketplaceName) {
        if (isEmpty(marketplaceName)) {
            return null;
        }
        return collectionMarketplaceRepository.findByName(marketplaceName)
                .orElseThrow(() -> new ServerException(ExceptionDetails.badRequest(INVALID_MARKETPLACE_NAME)));

    }

    @Named("retrieveNetwork")
    protected Network retrieveNetwork(String networkName) {
        if (isEmpty(networkName)) {
            return null;
        }
        return networkRepository.findByName(networkName)
                .orElseThrow(() -> new ServerException(ExceptionDetails.badRequest(INVALID_NETWORK_NAME)));
    }

    @Named("mapNftListFromDto")
    protected List<Nft> mapNftListFromDto(List<NftDto> nfts) {
        return nftMapper.fromDtoList(nfts);
    }

    @Named("mapNftListToDto")
    protected List<NftDto> mapNftListToDto(List<Nft> nfts) {
        return nftMapper.toDtoList(nfts);
    }

    @Named("retrieveCollectionType")
    protected CollectionType retrieveCollectionType(String collectionType) {
        try {
            return CollectionType.valueOf(collectionType);
        } catch (IllegalArgumentException e) {
            throw new ServerException(ExceptionDetails.badRequest(INVALID_COLLECTION_TYPE));
        }
    }

    @AfterMapping
    public void createLogEntity(@MappingTarget Collection collection) {
        collection.setLogEntity(new LogEntity());
    }
}
