package com.myprj.subwaycost.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StationInfoResponse {
    private String id;
    private String lineName;
    private String name;
}
