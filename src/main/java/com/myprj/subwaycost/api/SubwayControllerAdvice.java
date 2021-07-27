package com.myprj.subwaycost.api;

import com.myprj.subwaycost.api.dto.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SubwayControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public CommonResponse runtimeException(RuntimeException e){

        return CommonResponse.builder()
                .data(e.getLocalizedMessage())
                .build();
    }
}
