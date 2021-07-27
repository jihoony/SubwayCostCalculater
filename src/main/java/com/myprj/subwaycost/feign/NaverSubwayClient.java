package com.myprj.subwaycost.feign;

import com.myprj.subwaycost.domain.dto.ScheduleResponse;
import com.myprj.subwaycost.domain.dto.SubwayNumberObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "${feign.server.naver.subway.name}", url = "${feign.server.naver.subway.url}")
public interface NaverSubwayClient {
    @GetMapping("/transit/subway/stations/{stationId}/schedule")
    ScheduleResponse getSchedule(@PathVariable("stationId") String stationID);

    @GetMapping("/subway/provide")
    List<SubwayNumberObject> getSubwayNumberList(@RequestParam Map<String, Object> queryMap);
}
