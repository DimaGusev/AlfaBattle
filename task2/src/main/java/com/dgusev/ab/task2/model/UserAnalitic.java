package com.dgusev.ab.task2.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
public class UserAnalitic {

    private String userId;

    private BigDecimal totalSum;

    private Map<String, UserAnaliticCategorySummary> analyticInfo = new HashMap<>();
}
