package com.myprj.subwaycost.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimetableResponse {
    private String departureTime;
    private String headsign;
    private String type;
    private String laneId;
    private boolean lastTrip;
    private boolean firstTrip;
}
