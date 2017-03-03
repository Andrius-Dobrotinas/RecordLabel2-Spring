package com.andrewd.recordlabel.data.service;

import com.andrewd.recordlabel.data.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageSaveToRepositoryService implements FileSaveToRepositoryService {

    @Autowired
    ReleaseService svc;

    @Override
    public void save(ContentBase owner, String[] fileNames) {
        if (owner == null)
            throw new IllegalArgumentException("owner is null");
        if (fileNames == null)
            throw new IllegalArgumentException("fileNames is null");

        Image[] images = new Image[fileNames.length];

        for(int i = 0; i < fileNames.length; i++) {
            Image img = new Image();
            img.owner = owner;
            img.fileName = fileNames[i];
            images[i] = img;
        }

        svc.save(images);
    }
}