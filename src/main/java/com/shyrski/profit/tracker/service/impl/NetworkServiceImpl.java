package com.shyrski.profit.tracker.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shyrski.profit.tracker.model.db.Network;
import com.shyrski.profit.tracker.repository.NetworkRepository;
import com.shyrski.profit.tracker.service.NetworkService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NetworkServiceImpl implements NetworkService {

    private final NetworkRepository networkRepository;

    @Override
    public List<String> findAllNetworksNames() {
        List<Network> networks = networkRepository.findAll();

        return networks.stream()
                .map(Network::getName)
                .collect(Collectors.toList());
    }
}
