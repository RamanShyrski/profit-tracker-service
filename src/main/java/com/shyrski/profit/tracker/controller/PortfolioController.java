package com.shyrski.profit.tracker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shyrski.profit.tracker.exception.ExceptionDetails;
import com.shyrski.profit.tracker.model.dto.PortfolioDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@RequestMapping("/api/v1/portfolios")
public interface PortfolioController {

    @GetMapping
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Portfolios returned", response = PortfolioDto.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Unauthorized", response = ExceptionDetails.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ExceptionDetails.class)})
    @ApiOperation(value = "Find all portfolios for user", response = PortfolioDto.class, responseContainer = "List",
            produces = "application/json")
    List<PortfolioDto> findAllPortfoliosForUser();
}
