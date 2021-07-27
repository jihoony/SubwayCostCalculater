package com.myprj.subwaycost.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SubwayNumberObject {
    private ServiceRegion serviceRegion;
    private List<RealInfo> realInfo;
    private List<LogicalInfo> logicalInfo;
    private List<SubwayLineSection> subwayLineSection;
    private List<Stations> stations;
    private List<SubwayTotalLineSection> subwayTotalLineSection;
    private List<SimilarStations> similarStations;
    private List<AreaInfo> areaInfo;
    private SvgInfo svgInfo;
}
