package com.redhat.kubakc.controller;

import com.redhat.kubakc.model.GenericResponse;
import com.redhat.kubakc.model.Metadata;
import com.redhat.kubakc.repository.MetadataRepository;
import com.redhat.kubakc.service.KImageGenerator;
import com.redhat.kubakc.service.MetadataService;
import com.redhat.kubakp.model.Square;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@RestController
public class KStateImageController {
    private Logger logger = LoggerFactory.getLogger(KStateImageController.class);

    @Autowired
    KImageGenerator kImageGenerator;

    @Autowired
    GenericResponse genericResponse;

    @Autowired
    MetadataService metadataService;

    @Autowired
    MetadataRepository metadataRepository;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @GetMapping("topic/state")
    public ResponseEntity getImage() throws IOException {
        String base64Image = kImageGenerator.getBase64Image();
        if (base64Image == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(base64Image);
    }

    @DeleteMapping("topic/state")
    public ResponseEntity deleteImage() throws IOException {
        kImageGenerator.deleteImageFile();
        kImageGenerator.setSquare(new Square(0, 0, 0, "blue"));
        kImageGenerator.generateStateImage();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(genericResponse.getJsonPayload());
    }

    @GetMapping("/metadata")
    public ResponseEntity hostMetadata() {
        return ResponseEntity
                .ok()
                .header("content-type", "application/json")
                .body(genericResponse.getJsonPayload());
    }


}
