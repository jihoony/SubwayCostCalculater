package com.myprj.subwaycost.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class Body {
    private List<Item> items;
    private String numOfRows;
    private String pageNo;
    private String totalCount;
}
