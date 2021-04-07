package com.myprj.subwaycost.core;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BizDaysCalculate {
    BizDayCalculateResult calculate(LocalDate startDate, LocalDate endDate, List<LocalDate> holidays, Optional<List<LocalDate>> additionalOffDays);
}
