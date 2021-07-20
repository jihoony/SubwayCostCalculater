package com.myprj.subwaycost.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Item {
    private String subwayStationId;

    private String subwayRouteName;
    private String subwayStationName;

    private String arrTime;
    private String dailyTypeCode;
    private String depTime;
    private String endSubwayStationId;
    private String endSubwayStationNm;
    private String subwayRouteId;
    private String subwayStationNm;
    private String upDownTypeCode;

}