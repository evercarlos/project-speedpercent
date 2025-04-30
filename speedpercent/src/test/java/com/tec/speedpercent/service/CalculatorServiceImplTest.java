package com.tec.speedpercent.service;

import com.tec.speedpercent.domain.dto.CalculatorParameterRequestDto;
import com.tec.speedpercent.domain.dto.PercentageResponseDto;
import com.tec.speedpercent.service.impl.CalculatorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CalculatorServiceImplTest {

    @Mock
    private ExternalCalculateService externalService;

    @Mock
    private CallHistoryService callHistoryService;

    @InjectMocks
    private CalculatorServiceImpl calculatorService;

    private static final double NUMBER_ONE = 20;
    private static final double NUMBER_TWO = 30;
    private static final double PERCENTAGE = 10.0;

    @Test
    void shouldCalculatePercentageSuccessfully() {
        // Arrange
        CalculatorParameterRequestDto request = new CalculatorParameterRequestDto(NUMBER_ONE, NUMBER_TWO);
        PercentageResponseDto externalResponse = new PercentageResponseDto(PERCENTAGE, "", true);
        when(externalService.getPercentage()).thenReturn(externalResponse);

        // Act
        double result = calculatorService.calculatePercentage(request);

        // Assert
        double expected = (NUMBER_ONE + NUMBER_TWO) + ((NUMBER_ONE + NUMBER_TWO) * PERCENTAGE / 100);
        assertEquals(expected, result);

        verify(externalService, times(1)).getPercentage();
        verify(callHistoryService, times(1)).saveAsync(any());
    }
}
