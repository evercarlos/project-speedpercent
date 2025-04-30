package com.tec.speedpercent.service;

import com.tec.speedpercent.domain.dto.CalculatorParameterRequestDto;

public interface CalculatorService {

    double calculatePercentage(CalculatorParameterRequestDto calculatorParameterRequestDto);
}
