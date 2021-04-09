package com.myprj.subwaycost.api;

import com.myprj.subwaycost.core.DistanceBaseSubwayCostCalculateResult;
import com.myprj.subwaycost.service.CalculateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubwayCostCalculateController.class)
class SubwayCostCalculateControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CalculateService calculateService;


    @Test
    void cal_subwayCost_with_default_cost() throws Exception {

        LocalDate startDate = LocalDate.of(2021, 4, 7);
        DistanceBaseSubwayCostCalculateResult mockResult = DistanceBaseSubwayCostCalculateResult.builder()
                .starDate(startDate)
                .endDate(startDate.plusDays(30))
                .periods(30)
                .bizDays(22)
                .holidays(Arrays.asList(LocalDate.of(2021, 5, 5)))
                .cardCost(90_200L)
                .differenceCost(13_500L)
                .periodicalCost(76_700L)
                .build();

        given(calculateService.calculate(2_050L, startDate, null)).willReturn(mockResult);

        mvc
                .perform(get("/date/20210407"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("2021-05-05")))
        ;

    }

    @Test
    void cal_subwayCost_with_given_2050won_cost() throws Exception {

        LocalDate startDate = LocalDate.of(2021, 4, 7);
        DistanceBaseSubwayCostCalculateResult mockResult = DistanceBaseSubwayCostCalculateResult.builder()
                .starDate(startDate)
                .endDate(startDate.plusDays(30))
                .periods(30)
                .bizDays(22)
                .holidays(Arrays.asList(LocalDate.of(2021, 5, 5)))
                .cardCost(90_200L)
                .differenceCost(13_500L)
                .periodicalCost(76_700L)
                .build();

        given(calculateService.calculate(2_050L, startDate, null)).willReturn(mockResult);

        mvc
                .perform(get("/date/20210407?oneWayCost=2050"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("2021-05-05")))
        ;
    }

    @Test
    void cal_subwayCost_with_given_default_cost_and_additionalOffDays() throws Exception {

        LocalDate startDate = LocalDate.of(2021, 4, 7);
        DistanceBaseSubwayCostCalculateResult mockResult = DistanceBaseSubwayCostCalculateResult.builder()
                .starDate(startDate)
                .endDate(startDate.plusDays(30))
                .periods(30)
                .bizDays(22)
                .holidays(Arrays.asList(LocalDate.of(2021, 4, 8),
                        LocalDate.of(2021,4,9),
                        LocalDate.of(2021,5,5)
                        ))
                .cardCost(90_200L)
                .differenceCost(13_500L)
                .periodicalCost(76_700L)
                .build();

        final List<LocalDate> additionalOffDays = Arrays.asList(LocalDate.of(2021, 4, 8),
                LocalDate.of(2021, 4, 9));

        given(calculateService.calculate(2_050L, startDate, additionalOffDays)).willReturn(mockResult);

        mvc
                .perform(get("/date/20210407?addOffDay=20210408&addOffDay=20210409"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("2021-04-08")))
                .andExpect(content().string(containsString("2021-04-09")))
                .andExpect(content().string(containsString("2021-05-05")))
        ;
    }

}