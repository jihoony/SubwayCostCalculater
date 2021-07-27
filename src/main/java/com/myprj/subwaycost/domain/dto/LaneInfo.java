package com.myprj.subwaycost.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LaneInfo {
    private String id;
    private String name;
    private String longName;
    private LaneType type;
    private Operation operation;
    private String upWay;
    private String downWay;
}
