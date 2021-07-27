package com.myprj.subwaycost.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubwayLineSection {
    private String startStationId;
    private String endStationId;
    private LogicalLine logicalLine;
    private String style;
}
