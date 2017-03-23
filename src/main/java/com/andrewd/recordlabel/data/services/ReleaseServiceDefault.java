package com.andrewd.recordlabel.data.services;

import com.andrewd.recordlabel.common.BatchedResult;
import com.andrewd.recordlabel.common.components.BatchCountCalculator;
import com.andrewd.recordlabel.data.EntityDoesNotExistException;
import com.andrewd.recordlabel.data.SortDirection;
import com.andrewd.recordlabel.data.entities.Artist;
import com.andrewd.recordlabel.data.entities.Image;
import com.andrewd.recordlabel.data.entities.MediaType;
import com.andrewd.recordlabel.data.entities.Metadata;
import com.andrewd.recordlabel.data.entities.Release;
import com.andrewd.recordlabel.data.transformers.EntityToModelTransformer;
import com.andrewd.recordlabel.data.transformers.ModelToEntityTransformer;
import com.andrewd.recordlabel.data.repository.ReleaseRepository;
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

    @Override
    public void save(com.andrewd.recordlabel.supermodels.ReleaseSlim model) {
        Release entity = modelTransformer.getRelease(model);

        // Prevent changes to the collection of images and thumbnail from being saved with Release type
        if (entity.id != 0) {
            Release original = repository.getRelease(entity.id);
            if (original == null)
                throw new EntityDoesNotExistException(entity.id);
            entity.images = original.images;
            entity.thumbnail = original.thumbnail;
        }
        else {
            entity.images = null;
            entity.thumbnail = null;
        }

        repository.save(entity);
    }

    @Override
    public com.andrewd.recordlabel.supermodels.Release getRelease(int id) {
        Release entity = repository.getRelease(id);
        if (entity != null) {
            return entityTransformer.getRelease(entity);
        }
        return null;
    }

    @Override
    public com.andrewd.recordlabel.supermodels.ReleaseSlim getReleaseSlim(int id) {
        Release entity = repository.getRelease(id);
        if (entity != null) {
            return entityTransformer.getReleaseSlim(entity);
        }
        return null;
    }

    @Override
    public BatchedResult<com.andrewd.recordlabel.supermodels.Release> getReleases(int batchNumber, int batchSize) {
        List<Release> entities = repository.getReleases(batchNumber, batchSize, "title", SortDirection.DESCENDING);
        int totalCount = repository.getTotalReleaseCount();
        List<com.andrewd.recordlabel.supermodels.Release> superModels = entityTransformer.transformList(entities, entityTransformer::getRelease);

        BatchedResult<com.andrewd.recordlabel.supermodels.Release> result = new BatchedResult<>();
        result.entries = superModels;
        result.batchCount = BatchCountCalculator.calc(totalCount, batchSize);
        return result;
    }

    @Override
    public List<com.andrewd.recordlabel.supermodels.MediaType> getMediaTypeList() {
        List<MediaType> entities = repository.getMediaTypeList();
        return entityTransformer.transformList(entities, entityTransformer::getMediaType);
    }

    @Override
    public List<com.andrewd.recordlabel.supermodels.Metadata> getMetadataList() {
        List<Metadata> entities = repository.getMetadataList();
        return entityTransformer.transformList(entities, entityTransformer::getMetadata);
    }

    @Override
    public List<com.andrewd.recordlabel.supermodels.ArtistBarebones> getArtistBarebonesList() {
        List<Artist> entities = repository.getAllArtists();
        return entityTransformer.transformList(entities, entityTransformer::getArtistBarebones);
    }

    @Override
    public boolean objectExists(int id) {
        return repository.objectExists(id);
    }

    @Override
    public void save(Image[] images) {
        if (images == null) throw new IllegalArgumentException("images");
        repository.save(images);
    }
}