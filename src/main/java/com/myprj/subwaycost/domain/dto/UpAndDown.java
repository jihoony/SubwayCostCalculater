package com.myprj.subwaycost.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpAndDown {
    private String departureTime;
    private String headsign;
    private Operation operation;
    private String operationOrder;
    private String laneId;
    private boolean lastTrip;
    private boolean firstTrip;
}
