package com.myprj.subwaycost.domain;

import com.myprj.subwaycost.domain.dto.Item;
import com.myprj.subwaycost.domain.dto.XmlParserObject;
import com.myprj.subwaycost.feign.TagoAPIClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SubwayInfo {

    private static final String NUM_OF_ROWS = "500";

    @Value("${tago.serviceKey}")
    private String serviceKey;
    private DateFormat df = new SimpleDateFormat("HHmmss");

    private TagoAPIClient tagoAPIClient;

    public SubwayInfo(final TagoAPIClient tagoAPIClient) {
        this.tagoAPIClient = tagoAPIClient;
    }

    public XmlParserObject getSubwaySttnAcctoSchdulList(String subwayStationId, List<String> endSubwayStationIds) {

        HashMap<String, String> map = new HashMap<>();
        map.put("ServiceKey", serviceKey);
        map.put("subwayStationId", subwayStationId);
        map.put("dailyTypeCode", DailyType.WEEK_DAY.getCode());
        map.put("upDownTypeCode", UpDownType.UP.getCode());
        map.put("numOfRows", NUM_OF_ROWS);

        String nowTime = df.format(new Date());
        log.debug("nowTime: " + nowTime);

        XmlParserObject subwaySttnAcctoSchdulList = tagoAPIClient.getSubwaySttnAcctoSchdulList(map);

        List<Item> collect = subwaySttnAcctoSchdulList.getBody().getItems().stream()
                .filter(item -> isAfterNowTime(nowTime, item))
                .filter(item -> isMatchStationId(endSubwayStationIds, item))
                .sorted(Comparator.comparing(item -> item.getDepTime()))
                .collect(Collectors.toList());

        subwaySttnAcctoSchdulList.getBody().setItems(collect);

        return subwaySttnAcctoSchdulList;
    }

    private boolean isAfterNowTime(String nowTime, Item item)  {
        return (item.getDepTime().compareTo(nowTime) >= 0);
    }

    private boolean isMatchStationId(List<String> endSubwayStationIds, Item item) {
        return endSubwayStationIds == null ||
                endSubwayStationIds.stream()
                        .anyMatch(id -> id.equals(item.getEndSubwayStationId()));
    }

    public XmlParserObject getKwrdFndSubwaySttnList(String subwayStationName){

        HashMap<String, String> map = new HashMap<>();
        map.put("ServiceKey", serviceKey);
        map.put("subwayStationName", subwayStationName);

        XmlParserObject kwrdFndSubwaySttnList = tagoAPIClient.getKwrdFndSubwaySttnList(map);

        return kwrdFndSubwaySttnList;
    }

}
