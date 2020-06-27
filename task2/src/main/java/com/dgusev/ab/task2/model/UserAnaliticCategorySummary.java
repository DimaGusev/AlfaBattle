package com.dgusev.ab.task2.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserAnaliticCategorySummary {

    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal sum;
}
