package com.shyrski.profit.tracker.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shyrski.profit.tracker.mapper.mapstruct.PortfolioMapper;
import com.shyrski.profit.tracker.model.db.Portfolio;
import com.shyrski.profit.tracker.model.dto.PortfolioDto;
import com.shyrski.profit.tracker.repository.PortfolioRepository;
import com.shyrski.profit.tracker.service.PortfolioService;
import com.shyrski.profit.tracker.util.SecurityContextUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;

    @Override
    public List<PortfolioDto> findAllPortfoliosForUser() {
        String userId = SecurityContextUtil.getCurrentUserId();
        List<Portfolio> userPortfolios = portfolioRepository.findAllByUserId(userId);

        return portfolioMapper.toDtoList(userPortfolios);
    }
}
