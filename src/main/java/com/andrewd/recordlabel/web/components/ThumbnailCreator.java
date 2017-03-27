package com.andrewd.recordlabel.web.components;

import com.andrewd.recordlabel.supermodels.Image;

/**
 * Creates image thumbnails
 */
public interface ThumbnailCreator {

    /**
     * Creates a thumbnail for a given image
     * @param sourceImage image to create a thumbnail for
     * @throws Exception in case of any sort of exception
     */
    void createThumbnail(Image sourceImage) throws Exception;
}