package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.entities.Thumbnail;

public interface ThumbnailsRepository {
    Thumbnail save(Thumbnail thumbnail);
}