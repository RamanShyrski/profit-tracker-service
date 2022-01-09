package com.shyrski.profit.tracker.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shyrski.profit.tracker.mapper.NftMapper;
import com.shyrski.profit.tracker.model.db.Nft;
import com.shyrski.profit.tracker.model.dto.NftDto;
import com.shyrski.profit.tracker.repository.NftRepository;
import com.shyrski.profit.tracker.service.NftService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NftServiceImpl implements NftService {

    private final NftRepository nftRepository;
    private final NftMapper nftMapper;

    @Override
    public List<NftDto> findNftsInCollection(Long collectionId) {
        List<Nft> nfts = nftRepository.findAllByCollectionId(collectionId);

        List<NftDto> nftDtos = nftMapper.toDtoList(nfts);

        nftDtos.forEach(nftDto -> nftDto.setFloorPrice("100$"));
        return nftDtos;
    }
}
