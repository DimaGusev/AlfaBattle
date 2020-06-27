package com.dgusev.ab.task1.rest;

import com.dgusev.ab.task1.client.model.ATMDetails;
import com.dgusev.ab.task1.rest.model.AtmResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResponseConverter {

    public AtmResponse convert(ATMDetails atmDetails) {
        AtmResponse atmResponse = new AtmResponse();
        atmResponse.setDeviceId(atmDetails.getDeviceId());
        atmResponse.setCity(atmDetails.getAddress() != null ? atmDetails.getAddress().getCity() : "");
        atmResponse.setLocation(atmDetails.getAddress() != null ? atmDetails.getAddress().getLocation() : "");
        atmResponse.setPayments(atmDetails.getServices() != null && "Y".equals(atmDetails.getServices().getPayments()));
        atmResponse.setLatitude(atmDetails.getCoordinates() != null ? atmDetails.getCoordinates().getLatitude() : "");
        atmResponse.setLongitude(atmDetails.getCoordinates() != null ? atmDetails.getCoordinates().getLongitude() : "");
        return atmResponse;
    }
}
