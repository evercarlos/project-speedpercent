package com.tec.speedpercent.controller.v1;

import com.tec.speedpercent.domain.dto.CallHistoryResponseDto;
import com.tec.speedpercent.service.CallHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/histories", produces = "application/json")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CallHistoryController {

    private final CallHistoryService callHistoryService;

    @Operation(summary = "Lista historial de llamadas con paginacion", description = "Metodo de ordenación: \"id,asc\"")
    @GetMapping("withPagination")
    public Page<CallHistoryResponseDto> findAllPageable(
            @ParameterObject Pageable pageable) {
        return callHistoryService.findAllPageable(pageable);
    }

    @Operation(summary = "Lista historial de llamadas")
    @GetMapping
    public List<CallHistoryResponseDto> findAll() {
        return callHistoryService.findAll();
    }
}
