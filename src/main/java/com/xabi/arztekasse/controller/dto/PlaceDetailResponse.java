package com.xabi.arztekasse.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Schema(name = "PlaceDetailResponse")
public record PlaceDetailResponse(
        @Schema(description = "The actual response data")
        PlaceDetailsDto data
) {}