package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.entities.Thumbnail;

public interface ThumbnailsRepository {

    /**
     * Saves thumbnail metadata in the repository
     * @param thumbnail
     * @param ownerId object with which thumbnail is to be associated
     * @return id that is assigned to the thumbnail when it is being
     * saved
     */
    int save(Thumbnail thumbnail, int ownerId);

    Thumbnail get(int id);

    /**
     * Retrieves a thumbnail that is associated with a specific object
     * @param ownerId id of an object whose thumbnail is to be retrieved
     */
    Thumbnail getByOwner(int ownerId);
}