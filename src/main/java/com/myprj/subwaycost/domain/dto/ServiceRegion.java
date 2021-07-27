package com.myprj.subwaycost.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceRegion {
    private String code;
    private String name;
}
