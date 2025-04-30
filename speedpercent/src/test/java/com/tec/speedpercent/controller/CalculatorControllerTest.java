package com.tec.speedpercent.controller;


import com.tec.speedpercent.AbstractContextTest;
import com.tec.speedpercent.client.ExternalClient;
import com.tec.speedpercent.controller.v1.CalculatorController;
import com.tec.speedpercent.domain.dto.PercentageResponseDto;
import com.tec.speedpercent.repository.CallHistoryRepository;
import com.tec.speedpercent.service.CalculatorService;
import com.tec.speedpercent.service.CallHistoryService;
import com.tec.speedpercent.service.ExternalCalculateService;
import com.tec.speedpercent.service.impl.CalculatorServiceImpl;
import com.tec.speedpercent.service.impl.CallHistoryServiceImpl;
import com.tec.speedpercent.service.impl.ExternalCalculateServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {CalculatorController.class})
@ContextConfiguration(classes = {CalculatorControllerTest.TestConfig.class})
 class CalculatorControllerTest extends AbstractContextTest {

    private final String BASE_PATH = "/api/v1/calculator";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ExternalClient externalClient;

    @MockBean
    CacheManager cacheManager;

    @MockBean
    CallHistoryRepository callHistoryRepository;

    public static class TestConfig {


        @Bean
        CallHistoryService callHistoryService(CallHistoryRepository callHistoryRepository) {
            return new CallHistoryServiceImpl(callHistoryRepository);
        }

        @Bean
        ExternalCalculateService externalCalculateService(CacheManager cacheManager,
                                                          ExternalClient externalClient) {
            return new  ExternalCalculateServiceImpl(cacheManager, externalClient);
        }

        @Bean
        CalculatorService calculatorService(ExternalCalculateService externalCalculateService,
                                            CallHistoryService callHistoryService) {
            return new CalculatorServiceImpl(externalCalculateService, callHistoryService);
        }

        @Bean
        CalculatorController calculatorController(CalculatorService calculatorService) {
            return new CalculatorController(calculatorService);
        }
    }

    @Test
    void shouldCalculatePercentageSuccessfully() throws Exception {
        // arrange
        PercentageResponseDto response = new PercentageResponseDto(20, "", true);

        Mockito.when(externalClient.getPercentage()).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("numberOne", "34.0")
                .param("numberTwo", "54.0")
                .header("Authorization", DEFAULT_TOKEN)
        ).andExpect(status().isOk());
    }

}
