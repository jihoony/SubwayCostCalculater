package com.myprj.subwaycost.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RealInfo {
    private String id;
    private LogicalLine logicalLine;
    private String longitude;
    private String latitude;
    private String name;
    private String koName;
    private String enName;
    private String displayCode;
}
