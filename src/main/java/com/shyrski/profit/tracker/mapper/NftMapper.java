package com.shyrski.profit.tracker.mapper;

import java.nio.charset.StandardCharsets;
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
import com.shyrski.profit.tracker.model.dto.FileDto;
import com.shyrski.profit.tracker.model.dto.FileType;
import com.shyrski.profit.tracker.model.dto.nft.NftDto;
import com.shyrski.profit.tracker.model.dto.opensea.OpenSeaNftDto;
import com.shyrski.profit.tracker.service.S3BucketService;
import com.shyrski.profit.tracker.util.FileUtil;

@Mapper(componentModel = "spring")
public abstract class NftMapper {

    @Autowired
    private S3BucketService s3BucketService;

    @Value("${aws.s3.nft-image-bucket-name}")
    private String nftsBucketName;

    @Mapping(target = "image", source = "imageKey", qualifiedByName = "retrieveImageFromS3")
    public abstract NftDto toDto(Nft nft);

    @Mapping(target = "image", source = "imageUrl", qualifiedByName = "retrieveImage")
    @Mapping(target = "floorPrice", constant = "Unknown")
    @Mapping(target = "idInMarketplace", source = "id")
    @Mapping(target = "contractAddress", source = "assetContract.address")
    public abstract NftDto toDto(OpenSeaNftDto nft);

    public abstract List<NftDto> toDtoList(List<Nft> nft);

    public abstract List<NftDto> toDtoListFromOpenSeaDtoList(List<OpenSeaNftDto> nft);

    @Mapping(target = "imageKey", source = "image", qualifiedByName = "uploadImageToS3")
    public abstract Nft fromDto(NftDto nftDto);

    public abstract List<Nft> fromDtoList(List<NftDto> nftDto);

    @Named("retrieveImageFromS3")
    protected FileDto retrieveImageFromS3(String imageKey) {
        return FileDto.builder()
                .type(FileType.IMAGE)
                .content(s3BucketService.retrieveBased64Image(imageKey, nftsBucketName))
                .build();
    }

    @Named("retrieveImage")
    protected FileDto retrieveImage(String imageUrl) {
        return FileUtil.createFileFromUrl(imageUrl);
    }

    @Named("uploadImageToS3")
    protected String uploadImageToS3(FileDto file) {
        if (file.getType().equals(FileType.IMAGE)) {
            return s3BucketService.uploadImage(file.getContent(), nftsBucketName);
        }

        return s3BucketService.uploadFile(file.getContent().getBytes(StandardCharsets.UTF_8), nftsBucketName);
    }

    @AfterMapping
    public void createLogEntity(@MappingTarget Nft nft) {
        nft.setLogEntity(new LogEntity());
    }
}
