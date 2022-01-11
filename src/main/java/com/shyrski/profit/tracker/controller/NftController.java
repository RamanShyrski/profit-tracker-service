package com.shyrski.profit.tracker.controller;

import java.util.List;
import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shyrski.profit.tracker.exception.ExceptionDetails;
import com.shyrski.profit.tracker.model.dto.NftDto;
import com.shyrski.profit.tracker.model.dto.NftSearchDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "NFT Controller")
@RequestMapping("/api/v1/nfts")
public interface NftController {

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "NFTs returned",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NftDto.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ExceptionDetails.class)))})
    @Operation(summary = "Find all NFTs in collection")
    List<NftDto> findNftsInCollection(@Valid @ParameterObject NftSearchDto nftSearchDto);
}
