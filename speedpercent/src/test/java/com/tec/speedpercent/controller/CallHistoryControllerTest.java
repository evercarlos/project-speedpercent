package com.tec.speedpercent.controller;


import com.tec.speedpercent.AbstractContextTest;
import com.tec.speedpercent.controller.v1.CallHistoryController;
import com.tec.speedpercent.domain.entity.CallHistory;
import com.tec.speedpercent.repository.CallHistoryRepository;
import com.tec.speedpercent.service.CallHistoryService;
import com.tec.speedpercent.service.impl.CallHistoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {CallHistoryController.class})
@ContextConfiguration(classes = {CallHistoryControllerTest.TestConfig.class})
class CallHistoryControllerTest extends AbstractContextTest {

    private final String BASE_PATH = "/api/v1/histories";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CallHistoryRepository callHistoryRepository;

    public static class TestConfig {


        @Bean
        CallHistoryService callHistoryService(CallHistoryRepository callHistoryRepository) {
            return new CallHistoryServiceImpl(callHistoryRepository);
        }

        @Bean
        CallHistoryController callHistoryController(CallHistoryService callHistoryService) {
            return new CallHistoryController(callHistoryService);
        }

    }

    @Test
    void shouldCallHistorySuccessfully() throws Exception {
        // arrange
        CallHistory callHistory = new CallHistory();
        callHistory.setId(1);
        callHistory.setEndpoint("");

        Mockito.when(callHistoryRepository.findAll()).thenReturn(List.of(callHistory));

        // Act & Assert
        mockMvc.perform(get(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", DEFAULT_TOKEN)
        ).andExpect(status().isOk());
    }

    @Test
    void shouldReturnPagedCallHistorySuccessfully() throws Exception {
        // Arrange
        CallHistory callHistory1 = new CallHistory();
        callHistory1.setId(1);
        callHistory1.setEndpoint("endpoint1");

        CallHistory callHistory2 = new CallHistory();
        callHistory2.setId(2);
        callHistory2.setEndpoint("endpoint2");

        List<CallHistory> callHistories = List.of(callHistory1, callHistory2);

        Page<CallHistory> page = new PageImpl<>(callHistories, PageRequest.of(0, 2), callHistories.size());
        Mockito.when(callHistoryRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get(BASE_PATH + "/withPagination")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", DEFAULT_TOKEN)
                        .param("page", "0")
                        .param("size", "2")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(2))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(2));
    }
}
