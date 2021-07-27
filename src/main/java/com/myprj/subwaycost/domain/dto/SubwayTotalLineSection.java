package com.myprj.subwaycost.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubwayTotalLineSection {
    private String stationCode;
    private String totalLines;
    private String decos;
}
