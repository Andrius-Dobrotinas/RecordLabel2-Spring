package com.andrewd.recordlabel.data.service;

import com.andrewd.recordlabel.data.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageSaveToRepositoryService implements FileSaveToRepositoryService {

    @Autowired
    ReleaseService svc;

    @Override
    public void save(ContentBase owner, String[] relativePaths) {
        if (owner == null)
            throw new IllegalArgumentException("owner");
        if (relativePaths == null)
            throw new IllegalArgumentException("relativePaths");

        Image[] images = new Image[relativePaths.length];

        for(int i = 0; i < relativePaths.length; i++) {
            Image img = new Image();
            img.owner = owner;
            img.path = relativePaths[i];
            images[i] = img;
        }

        svc.save(images);
    }
}