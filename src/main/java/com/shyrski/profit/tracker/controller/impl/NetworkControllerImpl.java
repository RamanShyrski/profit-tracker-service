package com.shyrski.profit.tracker.controller.impl;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.shyrski.profit.tracker.controller.NetworkController;
import com.shyrski.profit.tracker.service.NetworkService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NetworkControllerImpl implements NetworkController {

    private final NetworkService networkService;

    @Override
    public List<String> findAllNetworkNames() {
        return networkService.findAllNetworksNames();
    }
}
