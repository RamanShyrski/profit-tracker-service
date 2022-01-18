package com.shyrski.profit.tracker.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Resource {

    PORTFOLIO("Portfolio"),
    NETWORK("Network"),
    MARKETPLACE("Marketplace"),
    OPENSEA_ADDRESS("OpenSea address"),
    COLLECTION_TYPE("Collection type");

    @Getter
    private final String value;
}
