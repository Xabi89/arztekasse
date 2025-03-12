package com.xabi.arztekasse.controller.dto;

import com.xabi.arztekasse.data.BusinessStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;

public record HoursDto(
        @Schema(example = "11:30:00") LocalTime startTime,
        @Schema(example = "15:00:00") LocalTime endTime,
        @Schema(example = "OPEN") BusinessStatus status
) {}
