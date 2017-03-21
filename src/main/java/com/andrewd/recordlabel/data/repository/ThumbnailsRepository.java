package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.entities.Thumbnail;

public interface ThumbnailsRepository {
    Thumbnail save(Thumbnail thumbnail);
    Thumbnail get(int id);

    /**
     * Retrieves a thumbnail that is associated with a specific object
     * @param ownerId id of an object whose thumbnail is to be retrieved
     */
    Thumbnail getByOwner(int ownerId);
}