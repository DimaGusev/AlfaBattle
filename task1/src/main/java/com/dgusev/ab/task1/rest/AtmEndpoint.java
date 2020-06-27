package com.dgusev.ab.task1.rest;

import com.dgusev.ab.task1.client.model.ATMDetails;
import com.dgusev.ab.task1.exception.AtmNotFoundException;
import com.dgusev.ab.task1.rest.model.AtmResponse;
import com.dgusev.ab.task1.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AtmEndpoint {

    @Autowired
    private AtmService atmService;

    @Autowired
    private ResponseConverter converter;

    @GetMapping(value = "/atms/{deviceId}")
    public AtmResponse findAtm(@PathVariable Integer deviceId) {
        ATMDetails atmDetails = atmService.getAtmByDeviceId(deviceId);
        if (atmDetails == null) {
            throw new AtmNotFoundException();
        }
        return converter.convert(atmDetails);
    }

    @GetMapping(value = "/atms/nearest")
    public AtmResponse findNearest(@RequestParam Double longitude, @RequestParam Double latitude, @RequestParam(required = false) Boolean payments) {
        ATMDetails atmDetails = atmService.findNearest(longitude, latitude, payments);
        if (atmDetails == null) {
            throw new AtmNotFoundException();
        }
        return converter.convert(atmDetails);
    }
}
