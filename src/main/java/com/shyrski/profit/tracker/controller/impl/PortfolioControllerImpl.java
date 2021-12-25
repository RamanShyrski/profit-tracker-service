package com.shyrski.profit.tracker.controller.impl;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.shyrski.profit.tracker.controller.PortfolioController;
import com.shyrski.profit.tracker.model.dto.PortfolioDto;
import com.shyrski.profit.tracker.service.PortfolioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PortfolioControllerImpl implements PortfolioController {

    private final PortfolioService portfolioService;

    @Override
    public List<PortfolioDto> findAllPortfoliosForUser() {
        return portfolioService.findAllPortfoliosForUser();
    }
}
