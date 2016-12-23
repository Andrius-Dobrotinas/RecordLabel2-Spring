package com.andrewd.recordlabel.data.service;

import com.andrewd.recordlabel.common.BatchedResult;
import com.andrewd.recordlabel.common.service.BatchCountCalculator;
import com.andrewd.recordlabel.data.SortDirection;
import com.andrewd.recordlabel.data.repository.ReleaseRepository;
import com.andrewd.recordlabel.data.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReleaseServiceDefault implements ReleaseService {

    @Autowired
    private ReleaseRepository repository;

    @Autowired
    private EntityToModelTransformer entityTransformer;

    @Autowired
    private ModelToEntityTransformer modelTransformer;

    public void save(com.andrewd.recordlabel.supermodel.ReleaseSlim model) {
        Release entity = modelTransformer.getRelease(model);
        repository.save(entity);
    }

    public com.andrewd.recordlabel.supermodel.Release getRelease(int id) {
        Release entity = repository.getRelease(id);
        if (entity != null) {
            return entityTransformer.getRelease(entity);
        }
        return null;
    }

    public com.andrewd.recordlabel.supermodel.ReleaseSlim getReleaseSlim(int id) {
        Release entity = repository.getRelease(id);
        if (entity != null) {
            return entityTransformer.getReleaseSlim(entity);
        }
        return null;
    }

    public BatchedResult<com.andrewd.recordlabel.supermodel.Release> getReleases(int batchNumber, int batchSize) {
        List<Release> entities = repository.getReleases(batchNumber, batchSize, "title", SortDirection.DESCENDING);
        int totalCount = repository.getTotalReleaseCount();
        List<com.andrewd.recordlabel.supermodel.Release> superModels = entityTransformer.transformList(entities, entityTransformer::getRelease);

        BatchedResult<com.andrewd.recordlabel.supermodel.Release> result = new BatchedResult<>();
        result.entries = superModels;
        result.batchCount = BatchCountCalculator.calc(totalCount, batchSize);
        return result;
    }

    public List<com.andrewd.recordlabel.supermodel.MediaType> getMediaTypeList() {
        List<MediaType> entities = repository.getMediaTypeList();
        return entityTransformer.transformList(entities, entityTransformer::getMediaType);
    }

    public List<com.andrewd.recordlabel.supermodel.Metadata> getMetadataList() {
        List<Metadata> entities = repository.getMetadataList();
        return entityTransformer.transformList(entities, entityTransformer::getMetadata);
    }

    public List<com.andrewd.recordlabel.supermodel.ArtistBarebones> getArtistBarebonesList() {
        List<Artist> entities = repository.getAllArtists();
        return entityTransformer.transformList(entities, entityTransformer::getArtistBarebones);
    }
}