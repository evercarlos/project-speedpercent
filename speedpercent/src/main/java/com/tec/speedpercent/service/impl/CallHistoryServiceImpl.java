package com.tec.speedpercent.service.impl;

import com.tec.speedpercent.domain.dto.CallHistoryRequestDto;
import com.tec.speedpercent.domain.dto.CallHistoryResponseDto;
import com.tec.speedpercent.exception.CalcErrorType;
import com.tec.speedpercent.exception.TransactionException;
import com.tec.speedpercent.helper.CallHistoryHelper;
import com.tec.speedpercent.mapper.CallHistoryMapper;
import com.tec.speedpercent.repository.CallHistoryRepository;
import com.tec.speedpercent.service.CallHistoryService;
import com.tec.speedpercent.util.BeanConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class CallHistoryServiceImpl implements CallHistoryService {

    private final CallHistoryRepository callHistoryRepository;

    public CallHistoryServiceImpl(CallHistoryRepository callHistoryRepository) {
        this.callHistoryRepository = callHistoryRepository;
    }

    @Override
    public Page<CallHistoryResponseDto> findAllPageable(Pageable pageable) {

        Sort sort = pageable.getSort().isUnsorted() ? Sort.by("id") : pageable.getSort();
        if (!CallHistoryHelper.validateSorName(sort)) {
            throw new TransactionException(HttpStatus.BAD_REQUEST, CalcErrorType.HISTORY_400_1.getDescription());
        }
        return callHistoryRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort))
                .map(CallHistoryMapper.MAPPER::callHistoryResponseDto);
    }

    @Override
    public List<CallHistoryResponseDto> findAll() {
        return callHistoryRepository.findAll().stream()
                .map(CallHistoryMapper.MAPPER::callHistoryResponseDto).toList();
    }

    @Async(BeanConstants.ASYNC_VIRTUAL_SAVE_CALL_HISTORY)
    @Override
    public CompletableFuture<Void> saveAsync(CallHistoryRequestDto callHistoryRequestDto) {
        log.info(STR."Running in thread: \{Thread.currentThread()}");
        try {
            var request = CallHistoryMapper.MAPPER.toCallHistory(callHistoryRequestDto);
            callHistoryRepository.save(request);
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }
}
