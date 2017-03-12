package com.andrewd.recordlabel.data.services;

import com.andrewd.recordlabel.supermodels.Image;
import java.util.List;

public interface ImagesService {

    /**
     * Retrieves a list of images for a specific object
     * @param ownerId Id of an object whose images are to be
     *                retrieved
     * @return a list of images
     */
    List<Image> getImages(int ownerId);

    /**
     * Retrieves an image that matches the provided id
     * @param imageId id of an image to retrieve
     * @return the image or null in case no image is found
     */
    Image get(int imageId);

    /**
     * Saves a list of images associating them with a specific
     * object.
     * @param ownerId Id of an object which images are to be
     *                assigned to
     * @param images a list of images to save
     * @return a list of saved images
     */
    List<Image> save(int ownerId, List<Image> images);

    void saveThumbnail(int objectId, String fileName);
}