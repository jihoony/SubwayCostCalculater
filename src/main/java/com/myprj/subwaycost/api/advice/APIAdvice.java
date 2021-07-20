package com.myprj.subwaycost.api.advice;

import com.myprj.subwaycost.api.dto.ComResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIAdvice {

    @ExceptionHandler(Exception.class)
    public ComResponse<Object> exceptionHandler(Exception e){

        return ComResponse.builder()
                .success(false)
                .message(e.getMessage())
                .items(null)
                .build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ComResponse<Object> runtimeExceptionHandler(RuntimeException e){

        return ComResponse.builder()
                .success(false)
                .message(e.getMessage())
                .items(null)
                .build();
    }
}
