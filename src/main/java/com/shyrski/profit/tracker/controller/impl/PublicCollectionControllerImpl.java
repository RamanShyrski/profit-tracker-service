package com.shyrski.profit.tracker.controller.impl;

import org.springframework.web.bind.annotation.RestController;

import com.shyrski.profit.tracker.controller.PublicCollectionController;
import com.shyrski.profit.tracker.service.impl.PublicCollectionServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PublicCollectionControllerImpl implements PublicCollectionController {

    private final PublicCollectionServiceImpl publicCollectionService;

    @Override
    public void syncCollections() {
        publicCollectionService.findAllCollectionInOpenSea();
    }
}
