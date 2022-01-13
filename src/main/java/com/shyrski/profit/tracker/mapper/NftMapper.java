package com.shyrski.profit.tracker.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.shyrski.profit.tracker.model.db.Nft;
import com.shyrski.profit.tracker.model.dto.nft.NftDto;
import com.shyrski.profit.tracker.service.S3BucketService;

@Mapper(componentModel = "spring")
public abstract class NftMapper {

    @Autowired
    private S3BucketService s3BucketService;

    @Value("${aws.s3.nft-image-bucket-name}")
    private String nftsBucketName;

    @Mapping(target = "image", source = "imageKey", qualifiedByName = "retrieveImageFromS3")
    public abstract NftDto toDto(Nft nft);

    public abstract List<NftDto> toDtoList(List<Nft> nft);

    @Named("retrieveImageFromS3")
    protected String retrieveImageFromS3(String imageKey) {
        return s3BucketService.retrieveBased64Image(imageKey, nftsBucketName);
    }
}
