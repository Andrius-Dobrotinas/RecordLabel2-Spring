package com.andrewd.recordlabel.data.services;

import com.andrewd.recordlabel.data.repository.ThumbnailsRepository;
import com.andrewd.recordlabel.data.transformers.EntityToModelTransformer;
import com.andrewd.recordlabel.data.transformers.ModelToEntityTransformer;
import com.andrewd.recordlabel.supermodels.Thumbnail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThumbnailsServiceDefault implements ThumbnailsService {

    @Autowired
    private ThumbnailsRepository repository;

    @Autowired
    private EntityToModelTransformer entityTransformer;

    @Autowired
    private ModelToEntityTransformer modelTransformer;

    @Override
    public Thumbnail save(Thumbnail thumbnail) {
        com.andrewd.recordlabel.data.entities.Thumbnail entity
                = modelTransformer.getThumbnail(thumbnail);

        repository.save(entity);

        return null;
        /* TODO: I'll need the original image id too.*/
    }

    @Override
    public Thumbnail get(int id) {
        return entityTransformer.getThumbnail(
                repository.get(id)
        );
    }

    @Override
    public Thumbnail getByOwner(int ownerId) {
        return entityTransformer.getThumbnail(
                repository.getByOwner(ownerId)
        );
    }
}