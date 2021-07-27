package com.myprj.subwaycost.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DaySchedule {
    private List<UpAndDown> up;
    private List<UpAndDown> down;
}
