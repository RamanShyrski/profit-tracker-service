package com.shyrski.profit.tracker.mapper;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shyrski.profit.tracker.model.db.LogEntity;
import com.shyrski.profit.tracker.model.db.Nft;
import com.shyrski.profit.tracker.model.db.NftType;
import com.shyrski.profit.tracker.model.dto.nft.NftDto;
import com.shyrski.profit.tracker.model.dto.opensea.OpenSeaNftDto;
import com.shyrski.profit.tracker.service.S3BucketService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NftMapper {

    private final S3BucketService s3BucketService;

    @Value("${aws.s3.nft-image-bucket-name}")
    private String nftsBucketName;

    public List<NftDto> toDtoListFromOpensea(List<OpenSeaNftDto> openSeaNftDtos) {
        List<NftDto> resultList = new ArrayList<>();

        openSeaNftDtos.forEach(openSeaNftDto -> {
            NftDto nftDto = new NftDto();

            nftDto.setImage(openSeaNftDto.getImageUrl());
            nftDto.setName(openSeaNftDto.getName());
            nftDto.setType(NftType.PUBLIC);
            nftDto.setDescription(openSeaNftDto.getDescription());
            nftDto.setIdInMarketplace(openSeaNftDto.getId());
            nftDto.setTokenId(openSeaNftDto.getTokenId());
            nftDto.setFloorPrice("Unknown");
            nftDto.setContractAddress(openSeaNftDto.getAssetContract().getAddress());

            resultList.add(nftDto);
        });

        return resultList;
    }

    public List<Nft> toDatabaseList(List<NftDto> nftDtos) {
        List<Nft> resultList = new ArrayList<>();

        nftDtos.forEach(dto -> {
            Nft nft = new Nft();
            nft.setNftId(dto.getNftId());
            nft.setName(dto.getName());

            if (dto.getNftId() == null) {
                if (isNotEmpty(dto.getImage())) {
                    nft.setImageKey(s3BucketService.uploadImage(dto.getImage(), nftsBucketName));
                }
            } else {
                nft.setImageKey(dto.getImage());
            }
            nft.setType(dto.getType());
            nft.setTokenId(dto.getTokenId());
            nft.setDescription(dto.getDescription());
            nft.setIdInMarketplace(String.valueOf(dto.getIdInMarketplace()));
            nft.setContractAddress(dto.getContractAddress());
            nft.setLogEntity(new LogEntity());

            resultList.add(nft);

        });

        return resultList;
    }

    public List<NftDto> toDtoList(List<Nft> nfts) {
        List<NftDto> resultList = new ArrayList<>();

        nfts.forEach(nft -> {
            NftDto dto = new NftDto();

            dto.setNftId(nft.getNftId());
            dto.setName(nft.getName());
            dto.setImage(nft.getImageKey());
            dto.setType(nft.getType());
            dto.setFloorPrice(null);
            dto.setTokenId(nft.getTokenId());
            dto.setIdInMarketplace(Long.valueOf(nft.getIdInMarketplace()));
            dto.setDescription(nft.getDescription());
            dto.setContractAddress(nft.getContractAddress());

            resultList.add(dto);
        });

        return resultList;
    }
}
