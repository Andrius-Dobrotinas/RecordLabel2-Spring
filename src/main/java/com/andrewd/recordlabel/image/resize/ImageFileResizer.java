package com.andrewd.recordlabel.image.resize;

import java.io.File;

/**
 * Resizes an image contained within a file
 */
public interface ImageFileResizer {

    /**
     * Resizes an image contained within the source file file and saves
     * the result into the destination file.
     * @param source an image file to be resized
     * @param destination a file to which the resized image is to be
     *                    written
     * @param imageFormatName an informal name of the image format
     * @param targetSize target size of the longest edge
     * @throws Exception is thrown in case of failure of any kind
     */
    void resize(File source,
                File destination,
                String imageFormatName,
                int targetSize)
            throws Exception;
}