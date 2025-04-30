package com.tec.speedpercent.domain.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record CalculatorParameterRequestDto(
        @NotNull(message = "El campo numberOne no puede ser nulo")
        @DecimalMin(value = "0.01", message = "El numberOne debe ser mayor a 0")
        double numberOne,

        @NotNull(message = "El campo numberTwo no puede ser nulo")
        @DecimalMin(value = "0.01", message = "El numberTwo debe ser mayor a 0")
        double numberTwo
) {
}
