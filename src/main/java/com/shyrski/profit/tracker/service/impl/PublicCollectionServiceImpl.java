package com.shyrski.profit.tracker.service.impl;

import org.springframework.stereotype.Service;

import com.shyrski.profit.tracker.client.OpenSeaClient;
import com.shyrski.profit.tracker.model.dto.opensea.AssetResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicCollectionServiceImpl {

    private final OpenSeaClient openSeaClient;

    public void findAllCollectionInOpenSea() {
        AssetResponseDto assetResponseDto = openSeaClient.findCollections("x0581Fd678b0B662b9A995d95E5B722c8778d0107");

        System.out.println();

    }

}
