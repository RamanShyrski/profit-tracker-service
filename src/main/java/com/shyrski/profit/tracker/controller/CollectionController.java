package com.shyrski.profit.tracker.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shyrski.profit.tracker.exception.ExceptionDetails;
import com.shyrski.profit.tracker.model.dto.CollectionDto;
import com.shyrski.profit.tracker.model.dto.CollectionSearchDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Collection controller")
@RequestMapping("/api/v1/collections")
public interface CollectionController {

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Collections returned",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CollectionDto.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ExceptionDetails.class)))})
    @Operation(summary = "Find NFT collections by portfolio id")
    List<CollectionDto> findAllCollectionsBySearchCriteria(@Valid CollectionSearchDto collectionSearchDto);
}
