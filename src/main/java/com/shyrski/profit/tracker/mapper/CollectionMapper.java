package com.shyrski.profit.tracker.mapper;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.shyrski.profit.tracker.model.db.Collection;
import com.shyrski.profit.tracker.model.db.Nft;
import com.shyrski.profit.tracker.model.dto.collection.CollectionDto;
import com.shyrski.profit.tracker.model.dto.opensea.OpenSeaCollectionDto;
import com.shyrski.profit.tracker.service.S3BucketService;
import com.shyrski.profit.tracker.util.FileUtil;

@Mapper(componentModel = "spring")
public abstract class CollectionMapper {

    @Autowired
    private S3BucketService s3BucketService;

    @Value("${aws.s3.collection-image-bucket-name}")
    private String collectionsImagesBucketName;

    @Mapping(target = "items", source = "nfts", qualifiedByName = "generateItems")
    @Mapping(target = "image", source = "imageKey", qualifiedByName = "retrieveImageFromS3")
    @Mapping(target = "network", source = "network.name")
    @Mapping(target = "marketplace", source = "collectionMarketplace.name")
    public abstract CollectionDto toDto(Collection collection);

    public abstract List<CollectionDto> toDtoList(List<Collection> collection);

    @Mapping(target = "idInMarketplace", source = "slug")
    @Mapping(target = "marketplace", constant = "opensea")
    @Mapping(target = "type", constant = "PUBLIC")
    @Mapping(target = "image", source = "imageUrl", qualifiedByName = "retrieveImage")
    public abstract CollectionDto toDto(OpenSeaCollectionDto openSeaCollectionDto);

    public abstract List<CollectionDto> toDtoListFromOpenSeaDtoList(List<OpenSeaCollectionDto> openSeaCollectionDto);

    @Named("generateItems")
    protected Long generateItems(List<Nft> nfts) {
        if (isEmpty(nfts)) {
            return 0L;
        }
        return (long) nfts.size();
    }

    @Named("retrieveImageFromS3")
    protected String retrieveImageFromS3(String imageKey) {
        return s3BucketService.retrieveBased64Image(imageKey, collectionsImagesBucketName);
    }

    @Named("retrieveImage")
    protected String retrieveImage(String imageUrl) {
        return FileUtil.convertImageToBase64(imageUrl);
    }
}
