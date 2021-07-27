package com.myprj.subwaycost.api;

import com.myprj.subwaycost.api.dto.CommonResponse;
import com.myprj.subwaycost.core.DistanceBaseSubwayCostCalculateResult;
import com.myprj.subwaycost.service.CalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin("*")
@RestController
public class SubwayCostCalculateController {

    private CalculateService calculateService;
    final private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public SubwayCostCalculateController(CalculateService calculateService) {
        this.calculateService = calculateService;
    }

    @GetMapping("/date/{yyyyMMdd}")
    public CommonResponse calculate(
            @PathVariable(name = "yyyyMMdd") String startDateParam,
            @RequestParam(name = "oneWayCost", required = false, defaultValue = "2050") Long oneWayCost,
            @RequestParam(name = "addOffDay", required = false) List<String> additionalOffDaysParam){

        log.info("startDateParam = " + startDateParam + ", oneWayCost = " + oneWayCost + ", additionalOffDaysParam = " + additionalOffDaysParam);

        final LocalDate startDate = convertLocalDate(startDateParam);
        final List<LocalDate> additionalOffDays = convertLocalDateList(additionalOffDaysParam);

        DistanceBaseSubwayCostCalculateResult calculate = calculateService.calculate(oneWayCost, startDate, additionalOffDays);

        return getCommonResponse(calculate);
    }

    private List<LocalDate> convertLocalDateList(List<String> additionalOffDaysParams) {

        if (additionalOffDaysParams != null && additionalOffDaysParams.size() > 0){
            return additionalOffDaysParams.stream().map(item
                    ->LocalDate.parse(item, dateTimeFormatter)).collect(Collectors.toList());
        }
        return null;
    }

    private LocalDate convertLocalDate(String startDateParam) {
        final LocalDate startDate = LocalDate.parse(startDateParam, dateTimeFormatter);
        return startDate;
    }

    @GetMapping("/recommend")
    public CommonResponse recommend(
            @RequestParam(name = "oneWayCost", required = false, defaultValue = "2050") Long oneWayCost,
            @RequestParam(name = "distance", required = false, defaultValue = "30") Long distance){

        log.info("oneWayCost = " + oneWayCost + ", distance = " + distance);

        List<DistanceBaseSubwayCostCalculateResult> recommend = calculateService.recommend(oneWayCost, distance);

        return getCommonResponse(recommend);
    }

    private CommonResponse getCommonResponse(Object object) {
        return CommonResponse.builder()
                .success(true)
                .data(object)
                .build();
    }
}
