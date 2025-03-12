package com.xabi.arztekasse.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record PlaceOverviewDto(
        @Schema(example = "1") long id,
        @Schema(example = "Le Café du Marché") String label,
        @Schema(example = "Rue de Conthey 17, 1950 Sion") String location
) {}
