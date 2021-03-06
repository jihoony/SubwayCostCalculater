package com.myprj.subwaycost.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponse {
    @Builder.Default
    private boolean success = false;
    private Object data;
}
