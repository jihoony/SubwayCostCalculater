package com.myprj.subwaycost.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Operation {
    private String type;
    private String name;
    private String shortName;
}
