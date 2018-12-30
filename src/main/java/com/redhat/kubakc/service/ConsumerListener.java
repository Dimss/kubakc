package com.redhat.kubakc.service;

import com.redhat.kubakc.controller.KStateImageController;
import com.redhat.kubakp.model.Square;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class ConsumerListener {

    private Logger logger = LoggerFactory.getLogger(KStateImageController.class);

    @Autowired
    KImageGenerator kImageGenerator;


    @KafkaListener(topics = "t10")
    public void consumeSquare(Square square, Acknowledgment acknowledgment, @Header(KafkaHeaders.OFFSET) int offset, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        square.setPartition(partition);
        square.setOffset(offset);
        logger.info("Consumed Message: " + square.toString());
        kImageGenerator.setSquare(square);
        kImageGenerator.generateStateImage();
    }

}
