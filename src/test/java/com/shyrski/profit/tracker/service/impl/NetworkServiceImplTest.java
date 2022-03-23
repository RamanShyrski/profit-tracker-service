package com.shyrski.profit.tracker.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shyrski.profit.tracker.model.db.Network;
import com.shyrski.profit.tracker.repository.NetworkRepository;

@ExtendWith(MockitoExtension.class)
class NetworkServiceImplTest {

    @InjectMocks
    private NetworkServiceImpl networkService;
    @Mock
    private NetworkRepository networkRepository;

    private static final String TEST_NETWORK_NAME1 = "testNetworkName1";
    private static final String TEST_NETWORK_NAME2 = "testNetworkName2";

    @Test
    public void shouldReturnCorrectNetworkNames() {
        Network network1 = createNetwork(TEST_NETWORK_NAME1);
        Network network2 = createNetwork(TEST_NETWORK_NAME2);

        List<String> expectedResult = List.of(TEST_NETWORK_NAME1, TEST_NETWORK_NAME2);

        List<Network> networks = new ArrayList<>(List.of(network1, network2));

        when(networkRepository.findAll()).thenReturn(networks);

        List<String> actualResult = networkService.findAllNetworksNames();

        assertEquals(actualResult, expectedResult);
    }

    private Network createNetwork(String name) {
        Network network = new Network();
        network.setName(name);
        return network;
    }
}