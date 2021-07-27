package com.myprj.subwaycost.domain;

import com.myprj.subwaycost.domain.dto.RealInfo;
import com.myprj.subwaycost.domain.dto.ScheduleResponse;
import com.myprj.subwaycost.domain.dto.SubwayNumberObject;
import com.myprj.subwaycost.domain.dto.UpAndDown;
import com.myprj.subwaycost.feign.NaverSubwayClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class NaverSubway {

    private Map<String, Object> map = new HashMap<>();
    private NaverSubwayClient client;

    public NaverSubway(NaverSubwayClient client) {
        this.client = client;

        map.put("readPath", "1000");
        map.put("version", "6.24");
        map.put("requestFile", "metaData.json");
    }

    public List<RealInfo> getStationInfo(String stationName) {
        List<SubwayNumberObject> subwayNumberList = client.getSubwayNumberList(map);

        final List<RealInfo> collect = subwayNumberList.stream()
                .map(subwayNumberObject ->
                        subwayNumberObject.getRealInfo().stream()
                                .filter(realInfo -> realInfo.getName().equals(stationName))
                                .collect(Collectors.toList()))
                .collect(Collectors.toList())
                .stream().flatMap(List::stream)
                .collect(Collectors.toList());

        return collect;
    }

    public List<UpAndDown> getFromToDest(String fromStation, String id, boolean direction, List<String> destStationList, String after) {

        final String stationId = getSubwayId(fromStation, id);
        log.debug("stationId: " + stationId);

        return getUpDownTimeSchedule(after, stationId, direction, destStationList);
    }



    private String getSubwayId(String stationName, String id) {

        final List<RealInfo> collect = getStationInfo(stationName);

        collect.stream()
                .forEach(realInfo -> log.debug(realInfo.toString()));

        final RealInfo resultInfo = collect.stream()
                .filter(realInfo -> realInfo.getId().equals(id))
                .findFirst().orElseThrow(()-> new RuntimeException("No Such SubwayName [" + stationName + "], id [" + id + "]"));

        log.debug("resultInfo = " + resultInfo);

        return resultInfo.getId();
    }



    private List<UpAndDown> getUpDownTimeSchedule(String after, String stationId, boolean direction, List<String> stationList) {

        final ScheduleResponse schedule = client.getSchedule(stationId);

        List<UpAndDown> directionCollection =
                direction ? schedule.getWeekdaySchedule().getUp() : schedule.getWeekdaySchedule().getDown();

        return directionCollection.stream()
                .filter(train-> filterTimeAfter(after, train))
                .filter(train-> filterStationNames(train, stationList))
                .collect(Collectors.toList())
                ;
    }

    private boolean filterTimeAfter(String formatDateTime, UpAndDown train) {
        return train.getDepartureTime().compareTo(formatDateTime) >= 0;
    }

    private boolean filterStationNames(UpAndDown train, List<String> stationList) {
        return stationList == null || stationList.size() == 0 || stationList.stream().anyMatch(station->station.equals(train.getHeadsign()));
    }
}
