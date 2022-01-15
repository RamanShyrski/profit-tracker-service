package com.shyrski.profit.tracker.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shyrski.profit.tracker.client.config.OpenSeaClientConfig;
import com.shyrski.profit.tracker.model.dto.opensea.OpenSeaAssetsResponse;
import com.shyrski.profit.tracker.model.dto.opensea.OpenSeaCollectionDto;

@FeignClient(value = "openSeaClient", url = "${opensea.base-url}", configuration = OpenSeaClientConfig.class)
public interface OpenSeaClient {

    @RequestMapping(method = RequestMethod.GET, value = "/collections")
    List<OpenSeaCollectionDto> findCollections(@RequestParam("asset_owner") String ownerAddress,
                                               @RequestParam("limit") Integer limit,
                                               @RequestParam("offset") Integer offset);

    @RequestMapping(method = RequestMethod.GET, value = "/assets")
    OpenSeaAssetsResponse findNftsInCollection(@RequestParam("owner") String ownerAddress,
                                               @RequestParam("collection") String collectionSlug,
                                               @RequestParam("limit") Integer limit,
                                               @RequestParam("offset") Integer offset);
}
