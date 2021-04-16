package com.myprj.subwaycost.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BizDayCalculateImplTest {
    public static final LocalDate HOLIDAY_2021_05_05 = LocalDate.of(2021, 5, 5);
    public static final LocalDate HOLIDAY_2021_05_19 = LocalDate.of(2021, 5, 19);
    public static final LocalDate HOLIDAY_2021_09_20 = LocalDate.of(2021, 9, 20);
    public static final LocalDate HOLIDAY_2021_09_21 = LocalDate.of(2021, 9, 21);
    public static final LocalDate HOLIDAY_2021_09_22 = LocalDate.of(2021, 9, 22);
    public static final LocalDate HOLIDAY_2021_08_15 = LocalDate.of(2021, 8, 15);

    List<LocalDate> holidays;
    BizDaysCalculate bizDaysCalculate;
    int daysToAdd;

    @BeforeEach
    void setUp(){
        daysToAdd = 30;
        holidays = new ArrayList<>();
    }

    void createCalculateBizDays(){
        bizDaysCalculate = new BizDayCalculateImpl();
    }

    void createCalculateBizDaysWithHoliday(List<LocalDate> holidays){
        bizDaysCalculate = new BizDayCalculateImpl();
    }

    @Test
    void calBizDays_v1(){
        System.out.println("CalculateSubwayCostTest.calBizDays_v1");

        LocalDate startDate = LocalDate.of(2021, 4, 1);
        LocalDate endDate = startDate.plusDays(daysToAdd);

        createCalculateBizDays();
        BizDayCalculateResult bizDayCalculateResult = bizDaysCalculate.calculate(startDate, endDate, holidays, Optional.empty());

        assertEquals(22, bizDayCalculateResult.getBizDays());
    }

    @Test
    void calBizDays_v2(){
        System.out.println("CalculateSubwayCostTest.calBizDays_v2");

        LocalDate startDate = LocalDate.of(2021, 4, 7);
        LocalDate endDate = startDate.plusDays(daysToAdd);

        createCalculateBizDays();
        BizDayCalculateResult bizDayCalculateResult = bizDaysCalculate.calculate(startDate, endDate, holidays, Optional.empty());

        assertEquals(23, bizDayCalculateResult.getBizDays());
    }

    @Test
    void calBizDaysWith1HolidaysThatStartDate(){
        System.out.println("CalculateSubwayCostTest.calBizDaysWith1HolidaysThatStartDate");

        LocalDate startDate = LocalDate.of(2021, 4, 7);
        LocalDate endDate = startDate.plusDays(daysToAdd);

        holidays.add(LocalDate.of(2021,4,7));

        createCalculateBizDaysWithHoliday(holidays);
        BizDayCalculateResult bizDayCalculateResult = bizDaysCalculate.calculate(startDate, endDate, holidays, Optional.empty());

        assertEquals(22, bizDayCalculateResult.getBizDays());
    }

    @Test
    void calBizDaysWith1Holidays(){
        System.out.println("CalculateSubwayCostTest.calBizDaysWith1Holidays");

        LocalDate startDate = LocalDate.of(2021, 4, 7);
        LocalDate endDate = startDate.plusDays(daysToAdd);

        holidays.add(HOLIDAY_2021_05_05);
        holidays.add(HOLIDAY_2021_05_19);
        holidays.add(HOLIDAY_2021_09_20);

        createCalculateBizDaysWithHoliday(holidays);
        BizDayCalculateResult bizDayCalculateResult = bizDaysCalculate.calculate(startDate, endDate, holidays, Optional.empty());

        assertEquals(22, bizDayCalculateResult.getBizDays());
    }

    @Test
    void calBizDaysWith2Holidays(){
        System.out.println("CalculateSubwayCostTest.calBizDaysWith2Holidays");

        LocalDate startDate = LocalDate.of(2021, 4, 20);
        LocalDate endDate = startDate.plusDays(daysToAdd);

        holidays.add(HOLIDAY_2021_05_05);
        holidays.add(HOLIDAY_2021_05_19);
        holidays.add(HOLIDAY_2021_09_20);

        createCalculateBizDaysWithHoliday(holidays);
        BizDayCalculateResult bizDayCalculateResult = bizDaysCalculate.calculate(startDate, endDate, holidays, Optional.empty());

        assertEquals(21, bizDayCalculateResult.getBizDays());
    }

    @Test
    void calBizDaysWithHolidaysAndAdditionalOffDays(){
        System.out.println("CalculateSubwayCostTest.calBizDaysWithHolidaysAndAdditionalOffDays");
        LocalDate startDate = LocalDate.of(2021, 4, 7);
        LocalDate endDate = startDate.plusDays(daysToAdd);

        holidays.add(HOLIDAY_2021_05_05);
        holidays.add(HOLIDAY_2021_05_19);
        holidays.add(HOLIDAY_2021_09_20);

        List<LocalDate> additionalOffDays = new ArrayList<>();
        additionalOffDays.add(LocalDate.of(2021, 4, 8));

        createCalculateBizDaysWithHoliday(holidays);
        BizDayCalculateResult bizDayCalculateResult = bizDaysCalculate.calculate(startDate, endDate, holidays, Optional.of(additionalOffDays));

        assertEquals(21, bizDayCalculateResult.getBizDays());
    }

    @Test
    void calBizDaysWithHolidaysAndDuplicateAdditionalOffDays(){
        System.out.println("CalculateSubwayCostTest.calBizDaysWithHolidaysAndDuplicateAdditionalOffDays");
        LocalDate startDate = LocalDate.of(2021, 4, 7);
        LocalDate endDate = startDate.plusDays(daysToAdd);

        holidays.add(HOLIDAY_2021_05_05);
        holidays.add(HOLIDAY_2021_05_19);
        holidays.add(HOLIDAY_2021_09_20);

        List<LocalDate> additionalOffDays = new ArrayList<>();
        additionalOffDays.add(LocalDate.of(2021, 5, 5));

        createCalculateBizDaysWithHoliday(holidays);
        BizDayCalculateResult bizDayCalculateResult = bizDaysCalculate.calculate(startDate, endDate, holidays, Optional.of(additionalOffDays));

        assertEquals(22, bizDayCalculateResult.getBizDays());
    }

    @Test
    void calBizDaysWithSundayHolidays(){
        System.out.println("CalculateSubwayCostTest.calBizDaysWithSundayHolidays");
        LocalDate startDate = LocalDate.of(2021, 8, 1);
        LocalDate endDate = startDate.plusDays(daysToAdd);

        holidays.add(HOLIDAY_2021_08_15);

        createCalculateBizDaysWithHoliday(holidays);
        BizDayCalculateResult bizDayCalculateResult = bizDaysCalculate.calculate(startDate, endDate, holidays, Optional.empty());

        assertEquals(22, bizDayCalculateResult.getBizDays());
    }
}