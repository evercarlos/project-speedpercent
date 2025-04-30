package com.tec.speedpercent.service;


import com.tec.speedpercent.domain.dto.CallHistoryRequestDto;
import com.tec.speedpercent.domain.entity.CallHistory;
import com.tec.speedpercent.repository.CallHistoryRepository;
import com.tec.speedpercent.service.impl.CallHistoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@ExtendWith(MockitoExtension.class)
class CallHistoryServiceTest {

    private final  String BASE_PATH = "/calculator";

    @Mock
    private CallHistoryRepository callHistoryRepository;

    @InjectMocks
    private CallHistoryServiceImpl callHistoryService;


    @Test
    void shouldCreateSuccessfully() {
        CallHistoryRequestDto callHistoryRequestDto = new CallHistoryRequestDto(
                LocalDateTime.now(),
                BASE_PATH,
                "{\\\"numberOne\\\":34.0,\\\"numberTwo\\\":54.0}",
                96.8,
                null
        );

        ArgumentCaptor<CallHistory> captor = ArgumentCaptor.forClass(CallHistory.class);
        Mockito.when(callHistoryRepository.save(Mockito.any(CallHistory.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CompletableFuture<Void> future = callHistoryService.saveAsync(callHistoryRequestDto);
        future.join();

        Mockito.verify(callHistoryRepository).save(captor.capture());
        CallHistory saved = captor.getValue();

        Assertions.assertEquals(callHistoryRequestDto.response(), saved.getResponse());
    }

    @Test
    void shouldThrowWhenTransactionFails() {
        CallHistoryRequestDto callHistoryRequestDto = new CallHistoryRequestDto(
                LocalDateTime.now(),
                BASE_PATH,
                "{\\\"numberOne\\\":34.0,\\\"numberTwo\\\":54.0}",
                96.8,
                null
        );

        Mockito.when(callHistoryRepository.save(Mockito.any(CallHistory.class)))
                .thenThrow(new RuntimeException("Error"));

        CompletableFuture<Void> future = callHistoryService.saveAsync(callHistoryRequestDto);
        Assertions.assertThrows(Exception.class, future::join);
    }
}
