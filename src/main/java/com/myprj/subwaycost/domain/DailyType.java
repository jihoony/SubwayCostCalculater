package com.myprj.subwaycost.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum DailyType {
    WEEK_DAY("01", "평일"),
    SATURDAY("02", "토요일"),
    SUNDAY("03", "일요일"),
    ;

    private String code;
    private String description;

    DailyType(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
