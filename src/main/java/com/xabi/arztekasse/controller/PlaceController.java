package com.xabi.arztekasse.controller;

import com.xabi.arztekasse.controller.dto.PlaceDetailResponse;
import com.xabi.arztekasse.controller.dto.PlaceOverviewResponse;
import com.xabi.arztekasse.controller.dto.ErrorResponseDto;
import com.xabi.arztekasse.controller.dto.PlaceDetailsDto;
import com.xabi.arztekasse.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/places")
@Slf4j
public class PlaceController {

    private final PlaceService placeService;

    @Operation(
            summary = "Get All Places",
            description = """
                    Retrieves an overview of all the places
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved place",
                    content = @Content(schema = @Schema(implementation = PlaceOverviewResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content())
    })
    @GetMapping
    public PlaceOverviewResponse getAllPlaces(){
        log.info("Fetching all the places");
        return new PlaceOverviewResponse(placeService.getAllPlaces());
    }

    @Operation(
            summary = "Get Place by ID",
            description = """
                    Retrieves a place's details by its unique ID
                    If place is closed for one day - it will return BusinessStatus=CLOSED, else OPEN
                    Days are represented as values:
                                    - `1` → Monday
                                    - `2` → Tuesday
                                    - `3` → Wednesday
                                    - `4` → Thursday
                                    - `5` → Friday
                                    - `6` → Saturday
                                    - `7` → Sunday
                    Optional parameter onlyOpenDays - default value "FALSE" to see all days of the place. True to filter
                    only the open days
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved place",
                    content = @Content(schema = @Schema(implementation = PlaceDetailResponse.class))),
            @ApiResponse(responseCode = "404", description = "Place not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content())
    })
    @GetMapping("/{placeId}")
    public PlaceDetailResponse getPlaceById(@PathVariable("placeId") long placeId, @RequestParam(value = "onlyOpenDays", defaultValue = "false") boolean onlyOpenDays){
        log.info("Fetching place with ID {}", placeId);
        return new PlaceDetailResponse(placeService.getPlaceById(placeId,onlyOpenDays));
    }
}
