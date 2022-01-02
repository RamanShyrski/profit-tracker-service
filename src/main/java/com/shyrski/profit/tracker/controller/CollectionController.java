package com.shyrski.profit.tracker.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shyrski.profit.tracker.exception.ExceptionDetails;
import com.shyrski.profit.tracker.model.dto.CollectionDto;
import com.shyrski.profit.tracker.model.dto.CollectionSearchDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@RequestMapping("/api/v1/collections")
public interface CollectionController {

    @GetMapping
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Collections returned", response = CollectionDto.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad request", response = ExceptionDetails.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ExceptionDetails.class),
            @ApiResponse(code = 500, message = "Internal Server Error",  response = ExceptionDetails.class)})
    @ApiOperation(value = "Find NFT collections by portfolio id", response = CollectionDto.class, responseContainer = "List",
            produces = "application/json")
    List<CollectionDto> findAllCollectionsBySearchCriteria(@Valid CollectionSearchDto collectionSearchDto);
}
