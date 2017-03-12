package com.andrewd.recordlabel.image;

/**
 * Thrown when no appropriate image writer for a given image format
 * is found
 */
public class AppropriateWriterNotFoundException extends Exception {

    private String imageFormatName;

    public String getImageFormatName() {
        return imageFormatName;
    }

    public AppropriateWriterNotFoundException(String imageFormatName) {
        super(String.format(
                "No appropriate writer found for %s format",
                imageFormatName));
    }
}