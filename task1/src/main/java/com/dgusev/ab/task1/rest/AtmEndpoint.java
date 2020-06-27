package com.dgusev.ab.task1.rest;

import com.dgusev.ab.task1.client.model.ATMDetails;
import com.dgusev.ab.task1.exception.AtmNotFoundException;
import com.dgusev.ab.task1.rest.model.AtmResponse;
import com.dgusev.ab.task1.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AtmEndpoint {

    @Autowired
    private AtmService atmService;

    @GetMapping(value = "/atms/{deviceId}")
    public AtmResponse findAtm(@PathVariable Integer deviceId) {
        ATMDetails atmDetails = atmService.getAtmByDeviceId(deviceId);
        if (atmDetails == null) {
            throw new AtmNotFoundException();
        }
        AtmResponse atmResponse = new AtmResponse();
        atmResponse.setDeviceId(deviceId);
        atmResponse.setCity(atmDetails.getAddress() != null ? atmDetails.getAddress().getCity() : "");
        atmResponse.setLocation(atmDetails.getAddress() != null ? atmDetails.getAddress().getLocation() : "");
        atmResponse.setPayments(!CollectionUtils.isEmpty(atmDetails.getAvailablePaymentSystems()));
        atmResponse.setLatitude(atmDetails.getCoordinates() != null ? atmDetails.getCoordinates().getLatitude() : "");
        atmResponse.setLongitude(atmDetails.getCoordinates() != null ? atmDetails.getCoordinates().getLongitude() : "");
        return atmResponse;
    }
}
