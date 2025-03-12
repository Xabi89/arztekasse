package com.xabi.arztekasse.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorResponseDto(@Schema(example = "404") int status,@Schema(example = "Place ID: 5 not found") String message) {}

