package com.tec.speedpercent.domain.dto;

import java.time.LocalDateTime;

public record CallHistoryResponseDto(
        int id,
        LocalDateTime date,
        String endpoint,
        String parameterJson,
        double response,
        String error
) {
}
