package com.redhat.kubakc.service;

import com.redhat.kubak.square.Square;
import org.apache.commons.io.FileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Service
public class KImageGenerator {
    private static final int IMAGE_WIDTH = 500;
    private static final int IMAGE_HEIGHT = 500;
    private static final String IMAGE_FILE_NAME = "topicState.jpg";
    private static final File IMAGE_FILE = new File(IMAGE_FILE_NAME);

    private Square square;

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public File getImageFile() {
        if (IMAGE_FILE.exists()) return IMAGE_FILE;
        return null;
    }

    public void deleteImageFile() {
        if (IMAGE_FILE.exists()) IMAGE_FILE.delete();
    }

    public void generateStateImage() {
        BufferedImage bf = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        try {
            if (IMAGE_FILE.exists()) bf = ImageIO.read(IMAGE_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = square.getX(); i < square.getX() + square.getSize(); i++) {
            for (int j = square.getY(); j < square.getY() + square.getSize(); j++) {
                bf.setRGB(i, j, square.getRgbColor());
            }
        }
        try {
            ImageIO.write(bf, "JPEG", new File(IMAGE_FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBase64Image() {
        File imageFile = getImageFile();
        if (imageFile == null) return null;
        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(imageFile);
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
