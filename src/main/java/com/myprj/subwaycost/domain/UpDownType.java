package com.myprj.subwaycost.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum UpDownType {

    UP("U", "상행"),
    DOWN("D", "하행"),
    ;

    private String code;
    private String description;

    UpDownType(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
