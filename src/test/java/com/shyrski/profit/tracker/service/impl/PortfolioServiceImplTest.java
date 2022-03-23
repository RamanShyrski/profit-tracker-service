package com.shyrski.profit.tracker.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.shyrski.profit.tracker.mapper.mapstruct.PortfolioMapper;
import com.shyrski.profit.tracker.model.db.Portfolio;
import com.shyrski.profit.tracker.model.dto.PortfolioDto;
import com.shyrski.profit.tracker.repository.PortfolioRepository;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceImplTest {

    @InjectMocks
    private PortfolioServiceImpl portfolioService;
    @Mock
    private PortfolioRepository portfolioRepository;
    @Mock
    private PortfolioMapper portfolioMapper;

    private static final String TEST_USER_ID = "testUserId";

    @Test
    public void shouldFindCorrectPortfoliosForUser() {
        Authentication authentication = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return TEST_USER_ID;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return null;
            }
        };

        SecurityContextHolder.getContext().setAuthentication(authentication);

        List<Portfolio> portfolios = new ArrayList<>(List.of(new Portfolio()));

        PortfolioDto portfolioDto = new PortfolioDto();
        List<PortfolioDto> expectedResult = List.of(portfolioDto);

        when(portfolioRepository.findAllByUserId(TEST_USER_ID)).thenReturn(portfolios);
        when(portfolioMapper.toDtoList(portfolios)).thenReturn(expectedResult);

        List<PortfolioDto> actualResult = portfolioService.findAllPortfoliosForUser();

        assertEquals(actualResult, expectedResult);
    }
}