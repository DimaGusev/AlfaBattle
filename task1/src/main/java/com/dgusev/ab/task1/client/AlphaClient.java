package com.dgusev.ab.task1.client;

import com.dgusev.ab.task1.client.model.ATMDetails;
import com.dgusev.ab.task1.client.model.BankATMDetails;
import com.dgusev.ab.task1.client.model.BankATMDetailsResponse;
import com.dgusev.ab.task1.config.Task1Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Component
public class AlphaClient {

    @Autowired
    private Task1Config config;

    @Autowired
    private RestTemplate restTemplate;

    public List<ATMDetails> getAllAtm() {
        URI uri = UriComponentsBuilder.fromHttpUrl(config.getApiPath()).pathSegment("/atm-service/atms").build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-ibm-client-id", config.getClientKey());
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<BankATMDetailsResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity, BankATMDetailsResponse.class);
        return response.getBody().getData().getAtms();
    }
}
