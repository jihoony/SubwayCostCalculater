package com.myprj.subwaycost.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogicalInfo {
    private String name;
    private String koName;
    private String enName;
    private String displayCode;
    private String imagePosX;
    private String imagePosY;
    private boolean express;
    private String id;
}
