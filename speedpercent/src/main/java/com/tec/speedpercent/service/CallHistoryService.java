package com.tec.speedpercent.service;

import com.tec.speedpercent.domain.dto.CallHistoryRequestDto;
import com.tec.speedpercent.domain.dto.CallHistoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CallHistoryService {

    Page<CallHistoryResponseDto> findAllPageable(Pageable pageable);

    List<CallHistoryResponseDto> findAll();

    CompletableFuture<Void> saveAsync(CallHistoryRequestDto callHistoryRequestDto);
}
