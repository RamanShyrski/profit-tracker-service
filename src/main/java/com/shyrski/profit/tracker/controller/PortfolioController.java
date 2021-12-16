package com.shyrski.profit.tracker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shyrski.profit.tracker.model.dto.PortfolioDto;
import com.shyrski.profit.tracker.service.PortfolioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping
    public List<PortfolioDto> findAllPortfoliosForUser() {
        return portfolioService.findAllPortfoliosForUser();
    }
}
