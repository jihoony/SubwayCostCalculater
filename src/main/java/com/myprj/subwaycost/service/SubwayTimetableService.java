package com.myprj.subwaycost.service;

import com.myprj.subwaycost.api.dto.StationInfoResponse;
import com.myprj.subwaycost.domain.NaverSubway;
import com.myprj.subwaycost.api.dto.TimetableResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SubwayTimetableService {

    private NaverSubway naverSubway;

    public SubwayTimetableService(NaverSubway naverSubway) {
        this.naverSubway = naverSubway;
    }

    public List<StationInfoResponse> getStationInfo(String stationName) {
        return naverSubway.getStationInfo(stationName).stream()
                .map(realInfo -> StationInfoResponse.builder()
                        .id(realInfo.getId())
                        .lineName(realInfo.getLogicalLine().getName())
                        .name(realInfo.getName())
                        .build())
                .collect(Collectors.toList())
                ;
    }

    public List<TimetableResponse> getTimetable(String stationName, String id, List<String> destStationName, LocalDateTime targetDate) {

        String after = targetDate.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return naverSubway.getFromToDest(stationName, id, true, destStationName, after).stream()
                .map(upAndDown -> TimetableResponse.builder()
                        .departureTime(upAndDown.getDepartureTime())
                        .type(upAndDown.getOperation().getType())
                        .headsign(upAndDown.getHeadsign())
                        .firstTrip(upAndDown.isFirstTrip())
                        .laneId(upAndDown.getLaneId())
                        .lastTrip(upAndDown.isLastTrip())
                        .build())
                .collect(Collectors.toList())
                ;
    }


}
