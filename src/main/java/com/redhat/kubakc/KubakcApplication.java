package com.redhat.kubakc;

import com.redhat.kubakc.controller.KStateImageController;
import com.redhat.kubakc.service.KImageGenerator;
import com.redhat.kubakp.model.Square;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KubakcApplication {
    private Logger logger = LoggerFactory.getLogger(KStateImageController.class);

    @Autowired
    KImageGenerator kImageGenerator;

    public static void main(String[] args) {
        SpringApplication.run(KubakcApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void EventListenerExecute() {
        logger.info("Generating default Kafka State Image");
        kImageGenerator.deleteImageFile();
        kImageGenerator.setSquare(new Square(0, 0, 0, "blue"));
        kImageGenerator.generateStateImage();
    }

}

