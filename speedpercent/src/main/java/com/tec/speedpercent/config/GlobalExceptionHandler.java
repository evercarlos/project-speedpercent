package com.tec.speedpercent.config;

import com.tec.speedpercent.config.dto.ErrorDto;
import com.tec.speedpercent.exception.TransactionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = TransactionException.class)
    public ResponseEntity<ErrorDto> businessExceptionHandler(TransactionException ex) {
        ErrorDto error = ErrorDto.builder()
                .status(ex.getStatus())
                .code(ex.getCode()).message(ex.getMessage()).build();
        return new ResponseEntity<>(error, ex.getStatus());
    }
}
