package com.xabi.arztekasse.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "PlaceOverviewResponse")
public record PlaceOverviewResponse(
        @Schema(description = "The actual response data")
        List<PlaceOverviewDto> data
) {}