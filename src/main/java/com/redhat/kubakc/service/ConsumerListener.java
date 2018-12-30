package com.redhat.kubakc.service;

import com.redhat.kubakc.controller.KStateImageController;
import com.redhat.kubakp.model.Square;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ConsumerListener implements ConsumerSeekAware {

    private Logger logger = LoggerFactory.getLogger(KStateImageController.class);
    private ConsumerSeekCallback consumerSeekCallback;
    private Map<TopicPartition, Long> map;

    @Autowired
    KImageGenerator kImageGenerator;


    @KafkaListener(topics = "t10")
    public void consumeSquare(Square square, @Header(KafkaHeaders.OFFSET) int offset, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        square.setPartition(partition);
        square.setOffset(offset);
        logger.info("Consumed Message: " + square.toString());
        kImageGenerator.setSquare(square);
        kImageGenerator.generateStateImage();
    }

    @Override
    public void registerSeekCallback(ConsumerSeekCallback consumerSeekCallback) {
        this.consumerSeekCallback = consumerSeekCallback;
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback consumerSeekCallback) {
        assignments.forEach((t, o) -> {
            consumerSeekCallback.seek(t.topic(), t.partition(), o - 3);
        });
    }

    @Override
    public void onIdleContainer(Map<TopicPartition, Long> map, ConsumerSeekCallback consumerSeekCallback) {
    }
}
