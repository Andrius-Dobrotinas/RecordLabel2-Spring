package com.andrewd.recordlabel.data.services;

import org.springframework.stereotype.Service;

@Service
public class ThumbnailsServiceDefault implements ThumbnailsService {

    @Override
    public void save(int objectId, String fileName) {

        /* TODO: make this thing require an argument of Thumbnail type
        instead of filename string. I'll need the original image id too.
         */
        // TODO: implement
        // probably create a separate service for thumbnails?
    }
}