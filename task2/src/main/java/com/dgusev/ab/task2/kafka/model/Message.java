package com.dgusev.ab.task2.kafka.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Message {

    private String ref;

    private Integer categoryId;

    private String userId;

    private String recipientId;

    private String desc;

    private BigDecimal amount;
}
