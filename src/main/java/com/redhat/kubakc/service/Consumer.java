package com.redhat.kubakc.service;

import com.redhat.kubakc.controller.KStateImageController;
import com.redhat.kubakp.model.Square;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private Logger logger = LoggerFactory.getLogger(KStateImageController.class);

    @Autowired
    KImageGenerator kImageGenerator;

    @KafkaListener(topics = "t10", groupId = "group1")
    public void consumeJson(Square square) {
        logger.info("Consumed Message: " + square.toString());
        kImageGenerator.setSquare(square);
        kImageGenerator.generateStateImage();

    }
}
