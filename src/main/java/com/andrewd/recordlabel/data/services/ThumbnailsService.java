package com.andrewd.recordlabel.data.services;

import com.andrewd.recordlabel.supermodels.Thumbnail;

public interface ThumbnailsService {
    Thumbnail save(Thumbnail thumbnail);
    Thumbnail get(int id);
    Thumbnail getByOwner(int ownerId);
}