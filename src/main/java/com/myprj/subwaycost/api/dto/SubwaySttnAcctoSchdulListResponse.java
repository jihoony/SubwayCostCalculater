package com.myprj.subwaycost.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubwaySttnAcctoSchdulListResponse {
    private String departureTime;
    private String arrivalTime;
    private String destinationStationId;
    private String destinationStationName;
}
