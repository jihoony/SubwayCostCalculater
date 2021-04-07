package com.myprj.subwaycost.core;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class BizDayCalculateResult {
    private LocalDate starDate;
    private LocalDate endDate;
    private long periods;
    private long bizDays;
    private List<LocalDate> holidays;
}
