package com.myprj.subwaycost.core;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class BizDayCalculateImpl implements BizDaysCalculate {

    @Override
    public BizDayCalculateResult calculate(@NonNull LocalDate startDate, LocalDate endDate, List<LocalDate> holidays, Optional<List<LocalDate>> additionalOffDays){

        if (log.isDebugEnabled()) {
            log.debug("startDate: " + startDate.toString());

            log.debug("holidays");
            holidays.forEach(item -> log.debug("-> " + item.toString()));
        }

        long daysToAdd = ChronoUnit.DAYS.between(startDate, endDate);

        Predicate<LocalDate> isHoliday = date -> holidays.contains(date);
        Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
        Predicate<LocalDate> isAdditionalOffDay = date -> additionalOffDays.isPresent() ? additionalOffDays.get().contains(date) : false;

        if (log.isDebugEnabled()) {
            additionalOffDays.ifPresent(list -> {
                log.debug("additional off days");
                list.forEach(item -> log.debug("-> " + item.toString()));
            });
        }

        long businessDays = Stream.iterate(startDate, date -> date.plusDays(1)).limit(daysToAdd + 1)
                .filter(isHoliday.or(isWeekend.or(isAdditionalOffDay)).negate()).count();

        List<LocalDate> selectedHolidays = Stream.iterate(startDate, date -> date.plusDays(1)).limit(daysToAdd + 1)
                .filter(isHoliday.or(isAdditionalOffDay)).collect(Collectors.toList());

        if (log.isDebugEnabled()) {
            log.debug("applied holidays count: " + selectedHolidays.stream().count());
            selectedHolidays.forEach(item -> log.debug("-> " + item.toString()));

            log.debug("businessDays: [" + businessDays + "]");
        }

        BizDayCalculateResult bizDayCalculateResult = BizDayCalculateResult.builder()
                .starDate(startDate)
                .endDate(startDate.plusDays(daysToAdd))
                .periods(daysToAdd)
                .bizDays(businessDays)
                .holidays(selectedHolidays)
                .build();

        return bizDayCalculateResult;
    }

}
