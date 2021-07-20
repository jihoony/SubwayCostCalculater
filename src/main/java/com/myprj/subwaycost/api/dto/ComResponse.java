package com.myprj.subwaycost.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ComResponse <T>{
    @Builder.Default
    private boolean success = true;

    @Builder.Default
    private String message = "ok";

    @Builder.Default
    private List<T> items = new ArrayList<>();
}
