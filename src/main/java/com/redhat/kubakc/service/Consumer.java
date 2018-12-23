package com.redhat.kubakc.service;

import com.redhat.kubakp.model.Square;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @Autowired
    KImageGenerator kImageGenerator;

    @KafkaListener(topics = "t10", groupId = "group1")
    public void consumeJson(Square square) {
        System.out.println("Consumed Message: " + square.toString());
        kImageGenerator.setSquare(square);
        kImageGenerator.generateStateImage();

    }
}
