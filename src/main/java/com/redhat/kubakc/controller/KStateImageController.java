package com.redhat.kubakc.controller;

import com.redhat.kubakc.model.GenericResponse;
import com.redhat.kubakc.service.KImageGenerator;
import com.redhat.kubakp.model.Square;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

@RestController
public class KStateImageController {
    private Logger logger = LoggerFactory.getLogger(KStateImageController.class);

    @Autowired
    KImageGenerator kImageGenerator;

    @Autowired
    GenericResponse genericResponse;

    @GetMapping("topic/state")
    public ResponseEntity getImage() throws IOException {
        File imageFile = kImageGenerator.getImageFile();
        if (imageFile == null) return ResponseEntity.notFound().build();
        byte[] fileContent = FileUtils.readFileToByteArray(imageFile);
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(encodedString);
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


}
