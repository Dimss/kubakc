package com.redhat.kubakp.model;

public class Square {
    private int x;
    private int y;
    private int size;
    private String color;
    private int rgbColor;
    // Metadata
    private int partition;
    private int offset;


    public Square() {

    }

    public Square(int x, int y, int size, String color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.rgbColor = this.getRgbColor();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getRgbColor() {
        switch (color) {
            case "red":
                return 0xFF0000;
            case "blue":
                return 0x00BFFF;
            case "green":
                return 0x00FF00;
            default:
                return 0x8B008B;
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setRgbColor(int rgbColor) {
        this.rgbColor = rgbColor;
    }


    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "Square{" +
                "x=" + x +
                ", y=" + y +
                ", size=" + size +
                ", rgbColor=" + rgbColor +
                '}';
    }
}
