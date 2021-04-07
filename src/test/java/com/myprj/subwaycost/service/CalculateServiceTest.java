package com.myprj.subwaycost.service;

import com.myprj.subwaycost.core.*;
import com.myprj.subwaycost.domain.Holiday;
import com.myprj.subwaycost.domain.HolidayRepository;
import com.myprj.subwaycost.domain.OffDate;
import com.myprj.subwaycost.domain.SubwayDistanceCostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

class CalculateServiceTest {

    @InjectMocks
    private CalculateService calculateService;

    @Mock
    private HolidayRepository holidayRepository;

    @Mock
    private SubwayDistanceCostRepository subwayDistanceCostRepository;

    private SubwayCostCalculate subwayCostCalculate;
    private BizDaysCalculate bizDaysCalculate;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private List<LocalDate> holidays;
    private List<Holiday> mockHoliday;

    private final Long DEFAULT_COST = 2_050L;

    @BeforeEach
    void setUp(){

        MockitoAnnotations.openMocks(this);

        bizDaysCalculate = new BizDayCalculateImpl();
        subwayCostCalculate = new SubwayCostCalculateImpl(subwayDistanceCostRepository, bizDaysCalculate);
        calculateService = new CalculateService(holidayRepository, subwayCostCalculate);

        holidays = Arrays.asList(
                LocalDate.of(2021, 5, 5),
                LocalDate.of(2021, 5, 19),
                LocalDate.of(2021,9,20),
                LocalDate.of(2021,9,21),
                LocalDate.of(2021,9,22)
        );

        mockHoliday = holidays.stream().map(day -> conventLocalDateToHoliday(day)).collect(Collectors.toList());

    }

    @Test
    void cal_service_with_default_cost(){
        LocalDate startDate = LocalDate.of(2021,4,7);
        LocalDate endDate = startDate.plusDays(30);

        given(holidayRepository.findAllByOffDateBetween(convertLocalDateToOffDay(startDate), convertLocalDateToOffDay(endDate))).willReturn(mockHoliday);

        SubwayCostCalculateResult calResult = calculateService.calculate(DEFAULT_COST, startDate, null);

        SubwayCostCalculateResult expectedCost = SubwayCostCalculateResult.builder()
                .starDate(startDate)
                .endDate(endDate)
                .periods(30)
                .bizDays(22)
                .holidays(Arrays.asList(LocalDate.of(2021, 5, 5)))
                .cardCost(90_200L)
                .differenceCost(13_500L)
                .periodicalCost(76_700L)
                .build();

        assertEquals(expectedCost, calResult);
    }

    @Test
    void cal_service_with_1200won_cost(){
        LocalDate startDate = LocalDate.of(2021,4,7);
        LocalDate endDate = startDate.plusDays(30);

        given(holidayRepository.findAllByOffDateBetween(convertLocalDateToOffDay(startDate), convertLocalDateToOffDay(endDate))).willReturn(mockHoliday);

        SubwayCostCalculateResult calResult = calculateService.calculate(1_200L, startDate, null);

        SubwayCostCalculateResult expectedCost = SubwayCostCalculateResult.builder()
                .starDate(startDate)
                .endDate(endDate)
                .periods(30)
                .bizDays(22)
                .holidays(Arrays.asList(LocalDate.of(2021, 5, 5)))
                .cardCost(52_800L)
                .differenceCost(-2_900L)
                .periodicalCost(55_700L)
                .build();

        assertEquals(expectedCost, calResult);
    }

    @Test
    void cal_service_with_1500won_cost(){
        LocalDate startDate = LocalDate.of(2021,4,7);
        LocalDate endDate = startDate.plusDays(30);

        given(holidayRepository.findAllByOffDateBetween(convertLocalDateToOffDay(startDate), convertLocalDateToOffDay(endDate))).willReturn(mockHoliday);

        SubwayCostCalculateResult calResult = calculateService.calculate(1_500L, startDate, null);

        SubwayCostCalculateResult expectedCost = SubwayCostCalculateResult.builder()
                .starDate(startDate)
                .endDate(endDate)
                .periods(30)
                .bizDays(22)
                .holidays(Arrays.asList(LocalDate.of(2021, 5, 5)))
                .cardCost(66_000L)
                .differenceCost(10_300L)
                .periodicalCost(55_700L)
                .build();

        assertEquals(expectedCost, calResult);
    }

    @Test
    void cal_service_with_default_cost_and_2addOffDay(){
        LocalDate startDate = LocalDate.of(2021,4,7);
        LocalDate endDate = startDate.plusDays(30);

        List<LocalDate> additionalOffDays = Arrays.asList(
                LocalDate.of(2021, 4, 8),
                LocalDate.of(2021, 4, 9)
        );

        given(holidayRepository.findAllByOffDateBetween(convertLocalDateToOffDay(startDate), convertLocalDateToOffDay(endDate))).willReturn(mockHoliday);

        SubwayCostCalculateResult calResult = calculateService.calculate(DEFAULT_COST, startDate, additionalOffDays);

        SubwayCostCalculateResult expectedCost = SubwayCostCalculateResult.builder()
                .starDate(startDate)
                .endDate(endDate)
                .periods(30)
                .bizDays(20)
                .holidays(Arrays.asList(
                        LocalDate.of(2021, 4, 8),
                        LocalDate.of(2021, 4, 9),
                        LocalDate.of(2021, 5, 5))
                )
                .cardCost(82_000L)
                .differenceCost(5_300L)
                .periodicalCost(76_700L)
                .build();

        assertEquals(expectedCost, calResult);
    }

    @Test
    void recommend_default_cost(){

        Long loopCnt = 30L;
        generateMockHolidays(loopCnt);

        List<SubwayCostCalculateResult> list = calculateService.recommend(DEFAULT_COST, loopCnt);
        assertEquals(loopCnt, list.size());
    }

    @Test
    void recommend_1200won_cost(){

        Long loopCnt = 30L;
        generateMockHolidays(loopCnt);

        List<SubwayCostCalculateResult> list = calculateService.recommend(1_200L, loopCnt);
        assertEquals(loopCnt, list.size());
    }

    @Test
    void recommend_default_cost_and_10distance(){

        Long loopCnt = 30L;
        generateMockHolidays(loopCnt);

        List<SubwayCostCalculateResult> list = calculateService.recommend(DEFAULT_COST, 10L);
        assertEquals(10, list.size());
    }

    private void generateMockHolidays(Long loopCnt) {
        LocalDate today = LocalDate.now();

        Stream.iterate(today, date -> date.plusDays(1)).limit(loopCnt + 1).forEach(startDate -> {

            LocalDate endDate = startDate.plusDays(30);
            Predicate<LocalDate> inScope = date -> !date.isBefore(startDate) && !date.isAfter(endDate);

            List<Holiday> newHoliday = mockHoliday.stream()
                    .map(date -> convertHolidayToLocalDate(date))
                    .filter(inScope)
                    .map(date -> conventLocalDateToHoliday(date))
                    .collect(Collectors.toList());

            given(holidayRepository.findAllByOffDateBetween(convertLocalDateToOffDay(startDate), convertLocalDateToOffDay(endDate))).willReturn(newHoliday);
        });
    }



    private LocalDate convertHolidayToLocalDate(Holiday item) {
        String startDateParam = item.getOffDate().getOffDate();
        return LocalDate.parse(startDateParam, dateTimeFormatter);
    }

    private OffDate convertLocalDateToOffDay(LocalDate startDate) {
        return OffDate.builder().offDate(startDate.format(dateTimeFormatter)).build();
    }

    private Holiday conventLocalDateToHoliday(LocalDate localDate) {
        return Holiday.builder()
                .offDate(convertLocalDateToOffDay(localDate))
                .description(localDate.format(dateTimeFormatter))
                .build();
    }

}