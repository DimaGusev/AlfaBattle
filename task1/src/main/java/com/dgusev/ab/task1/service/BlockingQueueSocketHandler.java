package com.dgusev.ab.task1.service;

import com.dgusev.ab.task1.client.ws.WsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;

@Slf4j
public class BlockingQueueSocketHandler extends StompSessionHandlerAdapter {

    private BlockingQueue<WsResponse> blockingQueue;

    public BlockingQueueSocketHandler(BlockingQueue<WsResponse> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        super.afterConnected(session, connectedHeaders);
        session.subscribe("/topic/alfik", this);
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        try {
            blockingQueue.put((WsResponse) payload);
        } catch (InterruptedException e) {
            log.error("Interrepted", e);
        }
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return WsResponse.class;
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        log.error("Unknown exception", exception);
    }
}
