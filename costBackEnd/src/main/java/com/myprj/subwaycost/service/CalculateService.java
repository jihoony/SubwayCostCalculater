package com.myprj.subwaycost.service;

import com.myprj.subwaycost.core.SubwayCostCalculate;
import com.myprj.subwaycost.core.DistanceBaseSubwayCostCalculateResult;
import com.myprj.subwaycost.domain.Holiday;
import com.myprj.subwaycost.domain.HolidayRepository;
import com.myprj.subwaycost.domain.OffDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class CalculateService {

    private final int DAYS_TO_ADD = 30;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private HolidayRepository holidayRepository;
    private SubwayCostCalculate subwayCostCalculate;

    public CalculateService(HolidayRepository holidayRepository, SubwayCostCalculate subwayCostCalculate) {
        this.holidayRepository = holidayRepository;
        this.subwayCostCalculate = subwayCostCalculate;
    }

    public DistanceBaseSubwayCostCalculateResult calculate(Long oneWayCost, LocalDate startDate, List<LocalDate> additionalOffDays) {

        LocalDate endDate = startDate.plusDays(DAYS_TO_ADD);
        List<LocalDate> holidays = getHolidays(startDate, DAYS_TO_ADD);

        if (log.isDebugEnabled()) {
            holidays.stream().forEach(holiday -> log.debug(holiday.toString()));
        }

        return subwayCostCalculate.calculate(oneWayCost, startDate, endDate, holidays, additionalOffDays);
    }

    private List<LocalDate> getHolidays(LocalDate startDate, int daysToAdd) {
        LocalDate endDate = startDate.plusDays(daysToAdd);

        log.debug("startDate: " + startDate);
        log.debug("endDate: " + endDate);

        List<Holiday> holidayEntities = holidayRepository.findAllByOffDateBetween(
                OffDate.builder().offDate(startDate.format(dateTimeFormatter)).build(),
                OffDate.builder().offDate(endDate.format(dateTimeFormatter)).build()
        );

        List<LocalDate> holidays = holidayEntities.stream()
                .map(item->LocalDate.parse(item.getOffDate().getOffDate(), dateTimeFormatter))
                .collect(Collectors.toList());

        return holidays;
    }

    public List<DistanceBaseSubwayCostCalculateResult> recommend(Long oneWayCost, Long distance) {

        LocalDate today = LocalDate.now();

        return Stream.iterate(today, date -> date.plusDays(1))
                .limit(distance)
                .map(date -> subwayCostCalculate.calculate(oneWayCost, date, date.plusDays(DAYS_TO_ADD), getHolidays(date, DAYS_TO_ADD), null))
                .sorted(Comparator.comparingLong(DistanceBaseSubwayCostCalculateResult::getDifferenceCost).reversed())
                .collect(Collectors.toList())
        ;

    }
}
