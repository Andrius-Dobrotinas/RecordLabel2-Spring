package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.entities.*;
import java.util.List;

/**
 * Stores and accesses image metadata
 */
public interface ImagesRepository {

    /**
     * Retrieves a list of images for a specific object
     * @param ownerId Id of an object whose images are to be
     *                retrieved
     * @return a list of images
     */
    List<Image> getImages(int ownerId);

    /**
     * Saves a list of images associating them with a specific
     * object
     * @param ownerId Id of an object to which these images are
     *                to be assigned
     * @param images images to be saved
     * @return an list of saved images
     */
    List<Image> saveImages(int ownerId, List<Image> images);
}