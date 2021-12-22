package com.shyrski.profit.tracker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shyrski.profit.tracker.model.dto.NftDto;
import com.shyrski.profit.tracker.service.NftService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/nfts")
@RequiredArgsConstructor
public class NftController {

    private final NftService nftService;

    @GetMapping
    public List<NftDto> findNftsInCollection(@RequestParam Long collectionId) {
        return nftService.findNftsInCollection(collectionId);
    }
}
