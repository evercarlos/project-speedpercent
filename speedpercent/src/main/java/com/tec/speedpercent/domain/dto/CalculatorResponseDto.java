package com.tec.speedpercent.domain.dto;

import java.math.BigDecimal;

public record CalculatorResponseDto(
        BigDecimal numberOne,
        BigDecimal numberTwo
) {}
