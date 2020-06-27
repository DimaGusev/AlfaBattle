package com.dgusev.ab.task1.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ATMDetails {

    private PostAddress address;

    private Integer deviceId;

    private List<String> availablePaymentSystems;

    private Coordinates coordinates;

}
