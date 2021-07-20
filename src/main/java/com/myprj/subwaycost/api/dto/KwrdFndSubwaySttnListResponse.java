package com.myprj.subwaycost.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KwrdFndSubwaySttnListResponse {
    private String code;
    private String name;
    private String description;
}
