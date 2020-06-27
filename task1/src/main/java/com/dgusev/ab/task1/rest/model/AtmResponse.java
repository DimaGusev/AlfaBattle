package com.dgusev.ab.task1.rest.model;

import lombok.Data;

@Data
public class AtmResponse {

    private String city;

    private Integer deviceId;

    private String latitude;

    private String location;

    private String longitude;

    private Boolean payments;

}
