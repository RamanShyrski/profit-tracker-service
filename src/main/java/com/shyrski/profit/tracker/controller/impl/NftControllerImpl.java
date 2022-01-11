package com.shyrski.profit.tracker.controller.impl;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.shyrski.profit.tracker.controller.NftController;
import com.shyrski.profit.tracker.model.dto.NftDto;
import com.shyrski.profit.tracker.model.dto.NftSearchDto;
import com.shyrski.profit.tracker.service.NftService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NftControllerImpl implements NftController {

    private final NftService nftService;

    @Override
    public List<NftDto> findNftsInCollection(NftSearchDto nftSearchDto) {
        return nftService.findNftsInCollection(nftSearchDto);
    }
}
