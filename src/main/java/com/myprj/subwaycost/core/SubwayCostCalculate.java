package com.myprj.subwaycost.core;

import java.time.LocalDate;
import java.util.List;

public interface SubwayCostCalculate {
    void setCost(Long oneWayCost);

    SubwayCostCalculateResult calculate(Long oneWayCost, LocalDate startDate, LocalDate endDate, List<LocalDate> holidays, List<LocalDate> additionalOffDays);
}
