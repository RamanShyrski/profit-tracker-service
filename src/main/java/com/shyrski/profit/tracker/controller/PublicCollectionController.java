package com.shyrski.profit.tracker.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/publicCollections")
public interface PublicCollectionController {

    @PostMapping
    void syncCollections();

}
