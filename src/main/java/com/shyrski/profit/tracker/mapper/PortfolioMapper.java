package com.shyrski.profit.tracker.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.shyrski.profit.tracker.model.db.Portfolio;
import com.shyrski.profit.tracker.model.dto.PortfolioDto;

@Mapper(componentModel = "spring")
public interface PortfolioMapper {

    PortfolioDto toDto(Portfolio portfolio);

    List<PortfolioDto> toDtoList(List<Portfolio> portfolios);
}
