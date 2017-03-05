package com.andrewd.recordlabel.data.services;

import com.andrewd.recordlabel.supermodels.Image;
import java.util.List;

public interface ImagesService {

    List<Image> getImages(int ownerId);

    /**
     * Saves a list of images and assigns them to an object in the database.
     * If the object is not of the type that may contain images, an
     * exception must be thrown.
     * @param ownerId id of an object to which images are to be assigned
     * @param images a list of images to save
     * @return a list of saved images
     */
    List<Image> save(int ownerId, List<Image> images);
}