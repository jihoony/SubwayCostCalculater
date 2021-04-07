package com.myprj.subwaycost.core;

import com.myprj.subwaycost.domain.SubwayDistanceCostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SubwayCostCalculateImplTest {
    public static final LocalDate HOLIDAY_2021_05_05 = LocalDate.of(2021, 5, 5);
    public static final LocalDate HOLIDAY_2021_05_19 = LocalDate.of(2021, 5, 19);
    public static final LocalDate HOLIDAY_2021_09_20 = LocalDate.of(2021, 9, 20);
    public static final LocalDate HOLIDAY_2021_09_21 = LocalDate.of(2021, 9, 21);
    public static final LocalDate HOLIDAY_2021_09_22 = LocalDate.of(2021, 9, 22);

    BizDaysCalculate bizDaysCalculate;
    SubwayCostCalculate subwayCostCalculate;
    int daysToAdd;

    private SubwayDistanceCostRepository subwayDistanceCostRepository;

    @BeforeEach
    void setUp(){
        daysToAdd = 30;
        bizDaysCalculate = new BizDayCalculateImpl();

//        final long oneWayCost = 2_050L;

        subwayCostCalculate = new SubwayCostCalculateImpl(subwayDistanceCostRepository, bizDaysCalculate);
//        subwayCostCalculate.setCost(oneWayCost);
    }


    @Test
    void cal_SubwayCost_with_2050won(){
        LocalDate startDate = LocalDate.of(2021, 4, 7);
        LocalDate endDate = startDate.plusDays(daysToAdd);

        SubwayCostCalculateResult calculateSubwayCostResult = subwayCostCalculate.calculate(2_050L, startDate, endDate, new ArrayList<>(), null);
        System.out.println("calResult = " + calculateSubwayCostResult);

        assertNotNull(calculateSubwayCostResult.getHolidays());
        assertEquals(0, calculateSubwayCostResult.getHolidays().size());
        assertEquals(76_700L, calculateSubwayCostResult.getPeriodicalCost());
    }

    @Test
    void cal_SubwayCost_with_1200won(){
        LocalDate startDate = LocalDate.of(2021, 4, 7);
        LocalDate endDate = startDate.plusDays(daysToAdd);

        SubwayCostCalculateResult calculateSubwayCostResult = subwayCostCalculate.calculate(1_200L, startDate, endDate, new ArrayList<>(), null);
        System.out.println("calResult = " + calculateSubwayCostResult);

        assertNotNull(calculateSubwayCostResult.getHolidays());
        assertEquals(0, calculateSubwayCostResult.getHolidays().size());
        assertEquals(55_700L, calculateSubwayCostResult.getPeriodicalCost());
    }
    @Test
    void cal_SubwayCost_with_1500won(){
        LocalDate startDate = LocalDate.of(2021, 4, 7);
        LocalDate endDate = startDate.plusDays(daysToAdd);

        SubwayCostCalculateResult calculateSubwayCostResult = subwayCostCalculate.calculate(1_500L, startDate, endDate, new ArrayList<>(), null);
        System.out.println("calResult = " + calculateSubwayCostResult);

        assertNotNull(calculateSubwayCostResult.getHolidays());
        assertEquals(0, calculateSubwayCostResult.getHolidays().size());
        assertEquals(55_700L, calculateSubwayCostResult.getPeriodicalCost());
    }

    @Test
    void calSubwayCostWith1Holidays(){
        LocalDate startDate = LocalDate.of(2021, 4, 7);
        LocalDate endDate = startDate.plusDays(daysToAdd);

        List<LocalDate> holidays = new ArrayList<>();
        holidays.add(HOLIDAY_2021_05_05);

        SubwayCostCalculateResult calculateSubwayCostResult = subwayCostCalculate.calculate(2_050L, startDate, endDate, holidays, null);
        System.out.println("calResult = " + calculateSubwayCostResult);

        assertNotNull(calculateSubwayCostResult.getHolidays());
        assertEquals(1, calculateSubwayCostResult.getHolidays().size());
    }

    @Test
    void calSubwayCost1MatchHolidaysWith3Holidays(){
        LocalDate startDate = LocalDate.of(2021, 4, 7);
        LocalDate endDate = startDate.plusDays(daysToAdd);

        List<LocalDate> holidays = new ArrayList<>();
        holidays.add(HOLIDAY_2021_05_05);
        holidays.add(HOLIDAY_2021_05_19);
        holidays.add(HOLIDAY_2021_09_20);

        SubwayCostCalculateResult calculateSubwayCostResult = subwayCostCalculate.calculate(2_050L, startDate, endDate, holidays, null);
        System.out.println("calResult = " + calculateSubwayCostResult);

        assertNotNull(calculateSubwayCostResult.getHolidays());
        assertEquals(1, calculateSubwayCostResult.getHolidays().size());
    }

    @Test
    void calSubwayCost2MatchHolidaysWith3Holidays(){
        LocalDate startDate = LocalDate.of(2021, 4, 25);
        LocalDate endDate = startDate.plusDays(daysToAdd);

        List<LocalDate> holidays = new ArrayList<>();
        holidays.add(HOLIDAY_2021_05_05);
        holidays.add(HOLIDAY_2021_05_19);
        holidays.add(HOLIDAY_2021_09_20);

        SubwayCostCalculateResult calculateSubwayCostResult = subwayCostCalculate.calculate(2_050L, startDate, endDate, holidays, null);
        System.out.println("calResult = " + calculateSubwayCostResult);

        assertNotNull(calculateSubwayCostResult.getHolidays());
        assertEquals(2, calculateSubwayCostResult.getHolidays().size());
    }

    @Test
    void calSubwayCost3MatchHolidaysWith3HolidaysAnd1AdditionalOffDays(){
        LocalDate startDate = LocalDate.of(2021, 4, 25);
        LocalDate endDate = startDate.plusDays(daysToAdd);

        List<LocalDate> holidays = new ArrayList<>();
        holidays.add(HOLIDAY_2021_05_05);
        holidays.add(HOLIDAY_2021_05_19);
        holidays.add(HOLIDAY_2021_09_20);

        List<LocalDate> additionalOffDays = new ArrayList<>();
        additionalOffDays.add(LocalDate.of(2021, 4, 25));

        SubwayCostCalculateResult calculateSubwayCostResult = subwayCostCalculate.calculate(2_050L, startDate, endDate, holidays, additionalOffDays);
        System.out.println("calResult = " + calculateSubwayCostResult);

        assertNotNull(calculateSubwayCostResult.getHolidays());
        assertEquals(3, calculateSubwayCostResult.getHolidays().size());
    }
}