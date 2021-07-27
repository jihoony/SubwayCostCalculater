package com.myprj.subwaycost.api;

import com.myprj.subwaycost.api.dto.CommonResponse;
import com.myprj.subwaycost.api.dto.StationInfoResponse;
import com.myprj.subwaycost.api.dto.TimetableResponse;
import com.myprj.subwaycost.service.SubwayTimetableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
public class SubwayTimetableController {
    private SubwayTimetableService subwayTimetableService;

    public SubwayTimetableController(SubwayTimetableService subwayTimetableService) {
        this.subwayTimetableService = subwayTimetableService;
    }

    @GetMapping("/name/{stationName}")
    public CommonResponse getStationInfo(@PathVariable String stationName){

        List<StationInfoResponse> stationInfo = subwayTimetableService.getStationInfo(stationName);

        return getCommonResponse(stationInfo);
    }



    @GetMapping("/table/{stationName}/{id}")
    public CommonResponse getTimetable(@PathVariable String stationName,
                                       @PathVariable String id,
                                       @RequestParam(name = "dest", required = false) List<String> destStationName,
                                       @RequestParam(name = "hhmmss", required = false) String dateTime){

        LocalDateTime targetDate = getLocalDateTime(dateTime);

        List<TimetableResponse> timetable = subwayTimetableService.getTimetable(stationName, id, destStationName, targetDate);

        return getCommonResponse(timetable);
    }

    private CommonResponse getCommonResponse(Object object) {
        return CommonResponse.builder()
                .success(true)
                .data(object)
                .build();
    }

    private LocalDateTime getLocalDateTime(String dateTime) {

        if (dateTime == null || dateTime.trim().length() == 0){
            dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
        }

        LocalDateTime now = LocalDateTime.now();
        String yyyyMMdd = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        dateTime = yyyyMMdd + dateTime;

        log.debug("dateTime: " + dateTime);

        LocalDateTime targetDate = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return targetDate;
    }
}
