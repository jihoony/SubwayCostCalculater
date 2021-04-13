package com.myprj.subwaycost.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
public class DistanceBaseSubwayCostCalculateImpl implements SubwayCostCalculate {

    public static final long DEFAULT_ONE_WAY_COST = 2_050L;
    private long periodicalCost;
    private long oneWayCost;
    private long twoWayCost;

    private HashMap<Long, Long> stepCostMap = new HashMap<>();

    private BizDaysCalculate bizDaysCalculate;

    public DistanceBaseSubwayCostCalculateImpl( BizDaysCalculate bizDaysCalculate) {
        this.bizDaysCalculate = bizDaysCalculate;

        initializeStepCostMap();

        this.setCost(DEFAULT_ONE_WAY_COST);
    }

    private void initializeStepCostMap() {

        stepCostMap.put(1_450L, 55_700L);
        stepCostMap.put(1_550L, 58_000L);
        stepCostMap.put(1_650L, 61_700L);
        stepCostMap.put(1_750L, 65_500L);
        stepCostMap.put(1_850L, 69_200L);
        stepCostMap.put(1_950L, 72_900L);
        stepCostMap.put(2_050L, 76_700L);
        stepCostMap.put(2_150L, 80_400L);
        stepCostMap.put(2_250L, 84_200L);
        stepCostMap.put(2_350L, 87_900L);
        stepCostMap.put(2_450L, 91_600L);
        stepCostMap.put(2_550L, 95_400L);
        stepCostMap.put(2_650L, 99_100L);
        stepCostMap.put(2_750L, 102_900L);
    }

    @Override
    public void setCost(Long oneWayCost){
        this.periodicalCost = getPeriodicalCost(oneWayCost);
        this.oneWayCost = oneWayCost;
        this.twoWayCost = this.oneWayCost * 2;
    }

    private long getPeriodicalCost(Long oneWayCost) {

        return stepCostMap.entrySet()
                .stream()
                .filter((item) -> item.getKey() <= oneWayCost)
                .max(Comparator.comparingLong(Map.Entry::getKey))
                .orElse(getMinPeriodicalCost())
                .getValue();
    }

    private Map.Entry<Long, Long> getMinPeriodicalCost() {
        return stepCostMap.entrySet().stream().min(Comparator.comparingLong(Map.Entry::getKey)).get();
    }

    @Override
    public DistanceBaseSubwayCostCalculateResult calculate(Long oneWayCost, LocalDate startDate, LocalDate endDate, List<LocalDate> holidays, List<LocalDate> additionalOffDays) {
        setCost(oneWayCost);

        return calculate(startDate, endDate, holidays, additionalOffDays);
    }

    private DistanceBaseSubwayCostCalculateResult calculate(LocalDate startDate, LocalDate endDate, List<LocalDate> holidays, List<LocalDate> additionalOffDays){

        BizDayCalculateResult bizDayCalculateResult = bizDaysCalculate.calculate(startDate, endDate, holidays, Optional.ofNullable(additionalOffDays));

        long cardCost = getCardCost(bizDayCalculateResult.getBizDays());
        long differenceCost = cardCost - periodicalCost;

        DistanceBaseSubwayCostCalculateResult distanceBaseSubwayCostCalculateResult = DistanceBaseSubwayCostCalculateResult.builder()
                .startDate(bizDayCalculateResult.getStarDate())
                .endDate(bizDayCalculateResult.getEndDate())
                .periods(bizDayCalculateResult.getPeriods())
                .bizDays(bizDayCalculateResult.getBizDays())
                .holidays(bizDayCalculateResult.getHolidays())
                .periodicalCost(periodicalCost)
                .cardCost(cardCost)
                .differenceCost(differenceCost)
                .build();

        return distanceBaseSubwayCostCalculateResult;
    }

    private long getCardCost(long bizDays) {
        return twoWayCost * bizDays;
    }


}
