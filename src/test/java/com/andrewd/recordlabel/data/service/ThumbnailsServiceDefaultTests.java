package com.andrewd.recordlabel.data.service;

import com.andrewd.recordlabel.data.repository.ThumbnailsRepository;
import com.andrewd.recordlabel.data.services.ThumbnailsServiceDefault;
import com.andrewd.recordlabel.data.transformers.EntityToModelTransformer;
import com.andrewd.recordlabel.data.transformers.ModelToEntityTransformer;
import com.andrewd.recordlabel.supermodels.Thumbnail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ThumbnailsServiceDefaultTests {

    @InjectMocks
    ThumbnailsServiceDefault svc;

    @Mock
    ThumbnailsRepository repository;

    @Mock
    EntityToModelTransformer entityTransformer;

    @Mock
    ModelToEntityTransformer modelTransformer;

    private final int thumbId = 711;
    private final int ownerId = 6;
    private Thumbnail thumb;
    private com.andrewd.recordlabel.data.entities.Thumbnail entity;

    @Before
    public void init() {
        thumb = new Thumbnail();
        entity = new com.andrewd.recordlabel.data.entities.Thumbnail();
    }

    @Test
    public void save_mustTransformThumbnailSuperModelIntoEntity() {
        svc.save(thumb);

        Mockito.verify(modelTransformer, times(1))
                .getThumbnail(Matchers.eq(thumb));
    }

    @Test
    public void save_mustSaveTransformedEntityInRepository() {
        Mockito.when(modelTransformer
                .getThumbnail(Matchers.eq(thumb)))
                .thenReturn(entity);

        svc.save(thumb);

        Mockito.verify(repository, times(1))
                .save(Matchers.eq(entity));
    }

    @Test
    public void get_mustGetThumbnailFromTheRepository() {
        svc.get(thumbId);

        Mockito.verify(repository, times(1))
                .get(Matchers.eq(thumbId));
    }

    @Test
    public void get_mustTransformThumbnailEntityIntoSuperModel() {
        Mockito.when(repository
                .get(Matchers.eq(thumbId)))
                .thenReturn(entity);

        svc.get(thumbId);

        Mockito.verify(entityTransformer, times(1))
                .getThumbnail(Matchers.eq(entity));
    }

    @Test
    public void getByOwner_mustGetThumbnailFromTheRepository() {
        svc.getByOwner(ownerId);

        Mockito.verify(repository, times(1))
                .getByOwner(Matchers.eq(ownerId));
    }

    @Test
    public void getByOwner_mustTransformThumbnailEntityIntoSuperModel() {
        Mockito.when(repository
                .getByOwner(Matchers.eq(ownerId)))
                .thenReturn(entity);

        svc.getByOwner(ownerId);

        Mockito.verify(entityTransformer, times(1))
                .getThumbnail(Matchers.eq(entity));
    }
}