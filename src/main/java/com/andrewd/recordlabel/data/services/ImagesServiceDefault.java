package com.andrewd.recordlabel.data.services;

import com.andrewd.recordlabel.data.entities.Image;
import com.andrewd.recordlabel.data.repository.ImagesRepository;
import com.andrewd.recordlabel.data.transformers.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImagesServiceDefault implements ImagesService {

    @Autowired
    private ImagesRepository repository;

    @Autowired
    private EntityToModelTransformer entityTransformer;

    @Autowired
    private ModelToEntityTransformer modelTransformer;

    @Override
    public List<com.andrewd.recordlabel.supermodels.Image> getImages(int ownerId) {
        if (ownerId == 0)
            throw new IllegalArgumentException("ownerId is 0");

        List<Image> images = repository.getImages(ownerId);

        return entityTransformer
                .transformList(images, entityTransformer::getImage);
    }

    @Override
    public com.andrewd.recordlabel.supermodels.Image get(int imageId) {
        if (imageId == 0)
            throw new IllegalArgumentException("imageId is 0");

        Image entity = repository.get(imageId);
        return entityTransformer.getImage(entity);
    }

    @Override
    public List<com.andrewd.recordlabel.supermodels.Image> save(
            int ownerId, List<com.andrewd.recordlabel.supermodels.Image> images) {
        if (ownerId == 0)
            throw new IllegalArgumentException("ownerId is 0");

        List<Image> entities = modelTransformer
                .transformList(images, modelTransformer::getImage);

        entities = repository.saveImages(ownerId, entities);

        return entityTransformer
                .transformList(entities, entityTransformer::getImage);
    }
}