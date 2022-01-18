package com.shyrski.profit.tracker.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.shyrski.profit.tracker.model.db.LogEntity;
import com.shyrski.profit.tracker.model.db.Nft;
import com.shyrski.profit.tracker.model.dto.nft.NftDto;
import com.shyrski.profit.tracker.model.dto.opensea.OpenSeaNftDto;
import com.shyrski.profit.tracker.service.S3BucketService;

@Mapper(componentModel = "spring")
public abstract class NftMapper {

    @Autowired
    private S3BucketService s3BucketService;

    @Value("${aws.s3.nft-image-bucket-name}")
    private String nftsBucketName;

    @Mapping(target = "image", source = "imageKey")
    public abstract NftDto toDto(Nft nft);

    @Mapping(target = "image", source = "imageUrl")
    @Mapping(target = "floorPrice", constant = "Unknown")
    @Mapping(target = "type", constant = "CUSTOM")
    @Mapping(target = "idInMarketplace", source = "id")
    @Mapping(target = "contractAddress", source = "assetContract.address")
    public abstract NftDto toDto(OpenSeaNftDto nft);

    public abstract List<NftDto> toDtoList(List<Nft> nft);

    public abstract List<NftDto> toDtoListFromOpenSeaDtoList(List<OpenSeaNftDto> nft);

    @Mapping(target = "imageKey", source = "image", qualifiedByName = "uploadImageToS3")
    public abstract Nft fromDto(NftDto nftDto);

    public abstract List<Nft> fromDtoList(List<NftDto> nftDto);

    @Named("uploadImageToS3")
    protected String uploadImageToS3(String base64EncodedImage) {
        return s3BucketService.uploadImage(base64EncodedImage, nftsBucketName);
    }

    @AfterMapping
    public void createLogEntity(@MappingTarget Nft nft) {
        nft.setLogEntity(new LogEntity());
    }
}
