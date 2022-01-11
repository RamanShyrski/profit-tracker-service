package com.shyrski.profit.tracker.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shyrski.profit.tracker.model.dto.opensea.AssetResponseDto;

@FeignClient(value = "openSeaClient", url = "${opensea.base-url}")
public interface OpenSeaClient {

    @RequestMapping(method = RequestMethod.GET, value = "/assets")
    AssetResponseDto findCollections(@RequestParam("owner") String owner);
}
