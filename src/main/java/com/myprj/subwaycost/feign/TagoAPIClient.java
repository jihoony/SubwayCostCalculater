package com.myprj.subwaycost.feign;

import com.myprj.subwaycost.config.FeignConfiguration;
import com.myprj.subwaycost.domain.dto.XmlParserObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@FeignClient(value = "${feign.server.tago.name}", url = "${feign.server.tago.url}", configuration = FeignConfiguration.class)
public interface TagoAPIClient {

    String GET_SUBWAY_STATION_ACCESS_TO_SCHEDULE_LIST = "/getSubwaySttnAcctoSchdulList";
    String GET_KOREAN_WORD_FIND_SUBWAY_STATION_LIST = "/getKwrdFndSubwaySttnList";

    @GetMapping(GET_SUBWAY_STATION_ACCESS_TO_SCHEDULE_LIST)
    XmlParserObject getSubwaySttnAcctoSchdulList(@RequestParam HashMap<String, String> map);

    @GetMapping(GET_KOREAN_WORD_FIND_SUBWAY_STATION_LIST)
    XmlParserObject getKwrdFndSubwaySttnList(@RequestParam HashMap<String, String> map);
}
