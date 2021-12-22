package com.shyrski.profit.tracker.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shyrski.profit.tracker.model.db.Nft;
import com.shyrski.profit.tracker.model.dto.NftDto;
import com.shyrski.profit.tracker.repository.NftRepository;
import com.shyrski.profit.tracker.service.NftService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NftServiceImpl implements NftService {

    private final NftRepository nftRepository;

    @Override
    public List<NftDto> findNftsInCollection(Long collectionId) {
        List<Nft> nfts = nftRepository.findAllByCollectionId(collectionId);
        return null;
    }
}
