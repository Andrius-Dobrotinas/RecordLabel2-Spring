package com.andrewd.recordlabel.image.resize.scalr;

import org.imgscalr.Scalr;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Resizes images using Scalr
 */
public interface ScalrResizer {

    /**
     * Resizes an image
     * @param image an image to resize
     * @param imageFormatName an informal name of the image format
     * @param targetSize size of the longest edge of the resulting image
     * @param scalingMethod method to be used when resizing
     * @param resizeMode
     * @return a resized image
     * @throws IOException
     */
    BufferedImage resize(BufferedImage image,
                         String imageFormatName,
                         int targetSize,
                         Scalr.Method scalingMethod,
                         Scalr.Mode resizeMode)
            throws IOException;
}