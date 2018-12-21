package com.redhat.kubakc.service;

import com.redhat.kubakp.model.Square;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    @KafkaListener(topics = "t10", groupId = "group1")
    public void consumeJson(Square square) {
        System.out.println("Consumed Message: " + square.toString());
    }
}
