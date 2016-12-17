package com.andrewd.recordlabel.data.service;

import com.andrewd.recordlabel.data.repository.ReleaseRepository;
import com.andrewd.recordlabel.data.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DefaultReleaseService implements ReleaseService {

    @Autowired
    private ReleaseRepository repository;

    @Autowired
    private EntityToModelTransformer entityTransformer;

    // TODO: change to super model
    public void save(Release entity) {
        repository.save(entity);
    }

    public com.andrewd.recordlabel.supermodel.Release getRelease(int id) {
        Release entity = repository.getRelease(id);
        if (entity != null) {
            return entityTransformer.getRelease(entity);
        }
        return null;
    }

    public List<com.andrewd.recordlabel.supermodel.MediaType> getMediaTypeList() {
        List<MediaType> entities = repository.getMediaTypeList();
        return entityTransformer.transformList(entities, entityTransformer::getMediaType);
    }
}