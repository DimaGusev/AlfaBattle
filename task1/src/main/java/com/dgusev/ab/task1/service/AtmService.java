package com.dgusev.ab.task1.service;

import com.dgusev.ab.task1.client.AlphaClient;
import com.dgusev.ab.task1.client.model.ATMDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AtmService {

    @Autowired
    private AlphaClient alphaClient;

    private Map<Integer, ATMDetails> atms;

    @PostConstruct
    public void init() {
        atms = alphaClient.getAllAtm().stream().collect(Collectors.toMap(ATMDetails::getDeviceId, Function.identity()));
    }

    public ATMDetails getAtmByDeviceId(Integer deviceId) {
        return atms.get(deviceId);
    }

}
