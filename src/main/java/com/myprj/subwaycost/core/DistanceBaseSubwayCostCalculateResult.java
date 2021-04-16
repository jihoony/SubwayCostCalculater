package com.myprj.subwaycost.core;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class DistanceBaseSubwayCostCalculateResult {

    private LocalDate startDate;
    private LocalDate endDate;
    private long periods;
    private long bizDays;
    private List<LocalDate> holidays;

    private long cardCost;
    private long differenceCost;
    private long periodicalCost;
    private long usingCount;
}
