package com.andrewd.recordlabel.supermodels;

public class Image {
    public int id;
    public String fileName;
    public int ownerId;

    public Image() {}

    public Image(String fileName) {
        this.fileName = fileName;
    }
}