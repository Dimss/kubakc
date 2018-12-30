package com.redhat.kubakc.task;

import com.redhat.kubakc.model.Metadata;
import com.redhat.kubakc.repository.MetadataRepository;
import com.redhat.kubakc.service.KImageGenerator;
import com.redhat.kubakc.service.MetadataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class Heartbeat {
    private static final Logger log = LoggerFactory.getLogger(Heartbeat.class);
    @Autowired
    MetadataService metadataService;
    @Autowired
    MetadataRepository metadataRepository;
    @Autowired
    KImageGenerator kImageGenerator;
    @Value("${app.service.port}")
    String appServicePort;

    @Scheduled(fixedRate = 1000)
    public void heartbeat() {
        String base64Image = kImageGenerator.getBase64Image();
        Metadata m = new Metadata(metadataService.getId(), metadataService.getHostname(), appServicePort, base64Image);
        metadataRepository.save(m);
        log.info("Heartbeat record updated");
    }
}
