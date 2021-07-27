package com.myprj.subwaycost.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ScheduleResponse {
    private TodayServiceDay todayServiceDay;
    private String stationId;
    private String upWay;
    private String downWay;
    private String upDirection;
    private String downDirection;
    private boolean hasExpressLaneType;
    private List<LaneInfo> lanes;
    private DaySchedule weekdaySchedule;
    private DaySchedule saturdaySchedule;
    private DaySchedule sundaySchedule;
}
