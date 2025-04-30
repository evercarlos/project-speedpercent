package com.tec.speedpercent.domain.dto;

public record PercentageResponseDto(
        double percentage,
        String message,
        boolean status
) {

}