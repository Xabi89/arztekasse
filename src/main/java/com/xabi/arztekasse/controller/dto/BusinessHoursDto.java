package com.xabi.arztekasse.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record BusinessHoursDto(@Schema(example = "2") int day, List<HoursDto> hours) {}
