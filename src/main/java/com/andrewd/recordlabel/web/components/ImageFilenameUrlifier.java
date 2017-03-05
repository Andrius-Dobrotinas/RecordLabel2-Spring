package com.andrewd.recordlabel.web.components;

import com.andrewd.recordlabel.supermodels.Image;

/**
 * Represents a function that simply replaces Image's filename
 * with a Url to this image.
 */
@FunctionalInterface
public interface ImageFilenameUrlifier {

    /**
     * Replaces Image's filename with a Url to this image
     * @param image
     * @param virtualPath virtual path where the image is located
     */
    void urlify(Image image, String virtualPath);
}