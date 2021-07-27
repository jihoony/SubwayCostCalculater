package com.myprj.subwaycost.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LaneType {
    private String id;
    private String name;
    private String longName;
    private String shortName;
    private String iconName;
    private String color;
    private String sortingPriority;
    private ServiceArea serviceArea;
}
