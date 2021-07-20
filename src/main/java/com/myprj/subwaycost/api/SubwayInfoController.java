package com.myprj.subwaycost.api;

import com.myprj.subwaycost.api.dto.ComResponse;
import com.myprj.subwaycost.service.SubwayInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class SubwayInfoController {

    private SubwayInfoService subwayInfoService;

    public SubwayInfoController(SubwayInfoService subwayInfoService) {
        this.subwayInfoService = subwayInfoService;
    }

    @GetMapping("/id/{subwayStationId}")
    public ComResponse<Object> getSubwaySttnAcctoSchdulList (
            @PathVariable String subwayStationId,
            @RequestParam(value = "dest", required = false) List<String> endSubwayStationIds) {

        return subwayInfoService.getSubwaySttnAcctoSchdulList(subwayStationId,  endSubwayStationIds);
    }

    @GetMapping("/name/{subwayStationName}")
    public ComResponse<Object> getKwrdFndSubwaySttnList(@PathVariable String subwayStationName){
        return subwayInfoService.getKwrdFndSubwaySttnList(subwayStationName);
    }
}
