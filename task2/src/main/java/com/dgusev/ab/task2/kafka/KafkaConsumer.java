package com.dgusev.ab.task2.kafka;

import com.dgusev.ab.task2.kafka.model.Message;
import com.dgusev.ab.task2.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class KafkaConsumer implements ConsumerSeekAware {

    @Autowired
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "RAW_PAYMENTS")
    public void consume(String message) throws IOException {
        log.info(String.format("#### -> Consumed message -> %s", message));
        try {
            Message model = objectMapper.readValue(message, Message.class);
            userService.addPayment(model);
        } catch (Exception ex) {
            log.debug("Invalid data", ex);
        }
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        assignments.keySet().stream().filter(partition->"RAW_PAYMENTS".equals(partition.topic()))
                .forEach(partition -> callback.seekToBeginning("RAW_PAYMENTS", partition.partition()));
    }

}
