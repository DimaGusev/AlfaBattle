package com.dgusev.ab.task1.service;

import com.dgusev.ab.task1.client.AlphaClient;
import com.dgusev.ab.task1.client.model.ATMDetails;
import com.dgusev.ab.task1.client.ws.WsRequest;
import com.dgusev.ab.task1.client.ws.WsResponse;
import com.dgusev.ab.task1.config.Task1Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AtmService {

    @Autowired
    private AlphaClient alphaClient;

    @Autowired
    private Task1Config config;

    private Map<Integer, ATMDetails> atms;

    @PostConstruct
    public void init() {
        atms = alphaClient.getAllAtm().stream().collect(Collectors.toMap(ATMDetails::getDeviceId, Function.identity()));
    }

    public ATMDetails getAtmByDeviceId(Integer deviceId) {
        return atms.get(deviceId);
    }

    public ATMDetails findNearest(Double longitude, Double latitude, Boolean payments) {
        return findNearest(longitude, latitude, payments, new HashSet<>());
    }

    public List<ATMDetails> findNearestWithMoney(Double longitude, Double latitude, Integer alfik) {
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        LinkedBlockingQueue<WsResponse> responses = new LinkedBlockingQueue<>();
        StompSession stompSession = null;
        try {
            stompSession = stompClient.connect(config.getSocketUrl(), new BlockingQueueSocketHandler(responses)).get();
        } catch (Exception e) {
            new RuntimeException("Cannot connect to websocket", e);
        }

        Integer totalAlfik = alfik;
        List<ATMDetails> list = new ArrayList<>();
        Set<Integer> found = new HashSet<>();
        while (totalAlfik > 0) {
            ATMDetails next = findNearest(longitude, latitude, null, found);
            if (next == null) {
                break;
            }
            Integer money = getMoneyByDeviceId(next.getDeviceId(), stompSession, responses);
            totalAlfik-=money;
            list.add(next);
            found.add(next.getDeviceId());
        }
        return list;
    }

    private ATMDetails findNearest(Double longitude, Double latitude, Boolean payments, Set<Integer> blackList) {
        Double distance = Double.MAX_VALUE;
        ATMDetails atm = null;
        for (ATMDetails atmDetails: atms.values()) {
            if (blackList.contains(atmDetails.getDeviceId()) || atmDetails.getCoordinates() == null || atmDetails.getCoordinates().getLongitude() == null || atmDetails.getCoordinates().getLatitude() == null) {
                continue;
            }
            Double newDistance = Math.sqrt(Math.pow((longitude - Double.valueOf(atmDetails.getCoordinates().getLongitude())),2) + Math.pow((latitude - Double.valueOf(atmDetails.getCoordinates().getLatitude())),2));
            if (newDistance < distance) {
                if (payments == null || !payments || "Y".equals(atmDetails.getServices().getPayments())) {
                    atm = atmDetails;
                    distance = newDistance;
                }
            }
        }
        return atm;
    }

    private Integer getMoneyByDeviceId(Integer deviceId, StompSession stompSession, BlockingQueue<WsResponse> responseQueue) {
        try {
            log.info("Send " + deviceId);
            stompSession.send("/", new WsRequest(deviceId));
            WsResponse wsResponse = responseQueue.take();
            return wsResponse.getAlfik();
        } catch (Exception ex) {
            throw new RuntimeException("Cannot receive money info", ex);
        }
    }
}
