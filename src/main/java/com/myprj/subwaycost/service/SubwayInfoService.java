package com.myprj.subwaycost.service;

import com.myprj.subwaycost.api.dto.*;
import com.myprj.subwaycost.domain.SubwayInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SubwayInfoService {

    private SubwayInfo subwayInfo;

    public SubwayInfoService(final SubwayInfo subwayInfo) {
        this.subwayInfo = subwayInfo;
    }

    public ComResponse<Object> getSubwaySttnAcctoSchdulList(String subwayStationId, List<String> endSubwayStationIds) {

        return ComResponse.builder()
                .items(subwayInfo.getSubwaySttnAcctoSchdulList(subwayStationId, endSubwayStationIds).getBody().getItems()
                        .stream().map(item -> SubwaySttnAcctoSchdulListResponse.builder()
                                .arrivalTime(item.getArrTime())
                                .departureTime(item.getDepTime())
                                .destinationStationId(item.getEndSubwayStationId())
                                .destinationStationName(item.getEndSubwayStationNm())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public ComResponse<Object> getKwrdFndSubwaySttnList(String subwayStationName){

        return ComResponse.builder()
                .items(subwayInfo.getKwrdFndSubwaySttnList(subwayStationName).getBody().getItems()
                        .stream().map(item -> KwrdFndSubwaySttnListResponse.builder()
                                .code(item.getSubwayStationId())
                                .name(item.getSubwayStationName())
                                .description(item.getSubwayRouteName())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
