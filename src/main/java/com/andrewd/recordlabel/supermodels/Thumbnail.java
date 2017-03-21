package com.andrewd.recordlabel.supermodels;

public class Thumbnail {
    public int id;
    public String fileName;
    public int ownerId;

    public Thumbnail() {
    }

    public Thumbnail(String fileName, int ownerId) {
        this.fileName = fileName;
        this.ownerId = ownerId;
    }
}