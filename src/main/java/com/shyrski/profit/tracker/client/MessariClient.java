package com.shyrski.profit.tracker.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shyrski.profit.tracker.model.dto.RateDto;

@FeignClient(value = "messariClient", url = "https://data.messari.io/api/v1")
public interface MessariClient {

    @RequestMapping(method = RequestMethod.GET, value = "/assets/{token}/metrics")
    RateDto findTokenRate(@PathVariable("token") String token);
}
