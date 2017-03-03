package com.andrewd.recordlabel.io;

public class FileHelper {

    private FileHelper() {}

    public static String getFileExtension(String fileName) {
        if (fileName == null)
            throw new IllegalArgumentException("fileName is null");

        int index = fileName.lastIndexOf('.');

        if (index == -1)
            return "";
        else
            return fileName.substring(index);
    }
}