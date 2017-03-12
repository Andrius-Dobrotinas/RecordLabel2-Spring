package com.andrewd.recordlabel.image.resize;

import java.io.*;

/**
 * Resizes an image to the target size
 */
public interface ImageResizer {

    /**
     * Resizes an image so that its longest edge matches the target size. .
     * @param imageInputStream input stream that contains an image to be resized
     * @param resizedImageOutputStream output stream a resized image is to be
     *                                 written to
     * @param imageFormatName an informal name of the image format
     * @param targetSize target size of the longest edge
     * @throws Exception in case of failure of any sort
     */
    void resizeImage(InputStream imageInputStream,
                     OutputStream resizedImageOutputStream,
                     String imageFormatName,
                     int targetSize) throws Exception;
}