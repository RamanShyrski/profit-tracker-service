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
import com.shyrski.profit.tracker.model.dto.CollectionDto;
import com.shyrski.profit.tracker.service.S3BucketService;

@Mapper(componentModel = "spring")
public abstract class CollectionMapper {

    @Autowired
    private S3BucketService s3BucketService;

    @Value("${aws.s3.collection-image-bucket-name}")
    private String collectionsImagesBucketName;

    @Mapping(target = "items", source = "nfts", qualifiedByName = "generateItems")
    @Mapping(target = "image", source = "imageKey", qualifiedByName = "retrieveImageFromS3")
    @Mapping(target = "network", source = "network.name")
    public abstract CollectionDto toDto(Collection collection);

    public abstract List<CollectionDto> toDtoList(List<Collection> collection);

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
}
