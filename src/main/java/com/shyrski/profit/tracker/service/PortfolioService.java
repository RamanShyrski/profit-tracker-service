package com.shyrski.profit.tracker.service;

import java.util.List;

import com.shyrski.profit.tracker.model.dto.PortfolioDto;

public interface PortfolioService {

    List<PortfolioDto> findAllPortfoliosForUser();
}
