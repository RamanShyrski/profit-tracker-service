package com.shyrski.profit.tracker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shyrski.profit.tracker.model.dto.CollectionDto;
import com.shyrski.profit.tracker.service.CollectionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/collections")
public class CollectionController {

    private final CollectionService collectionService;

    @GetMapping
    public List<CollectionDto> findAllCollectionsForPortfolio(@RequestParam Long portfolioId) {
        return collectionService.findAllCollections(portfolioId);
    }
}
