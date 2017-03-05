package com.andrewd.recordlabel.data.services;

import com.andrewd.recordlabel.data.entities.Image;
import com.andrewd.recordlabel.data.repository.ImagesRepository;
import com.andrewd.recordlabel.data.transformers.EntityToModelTransformer;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImagesServiceDefault implements ImagesService {

    @Autowired
    private ImagesRepository repository;

    @Autowired
    EntityToModelTransformer transformer;

    @Override
    public List<com.andrewd.recordlabel.supermodels.Image> getImages(int ownerId) {
        if (ownerId == 0)
            throw new IllegalArgumentException("ownerId is 0");

        List<Image> images = repository.getImages(ownerId);
        return transformer.transformList(images, transformer::getImage);
    }

    @Override
    public List<com.andrewd.recordlabel.supermodels.Image> save(
            int ownerId, List<com.andrewd.recordlabel.supermodels.Image> images) {
        // TODO: implement
        // should return a list of images WITH their ids
        return null;
    }
}