package com.andrewd.recordlabel.service;

import com.andrewd.recordlabel.data.model.*;
import com.andrewd.recordlabel.data.repository.ReleaseRepository;
import com.andrewd.recordlabel.data.service.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.*;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ReleaseServiceDefault_saveReleaseSlimTests {

    @InjectMocks
    ReleaseServiceDefault svc = new ReleaseServiceDefault();

    @Mock
    ReleaseRepository repository;

    @Mock
    EntityToModelTransformer entityTransformer;

    @Mock
    ModelToEntityTransformer modelTransformer;

    com.andrewd.recordlabel.supermodel.ReleaseSlim superModel;
    Release entity;
    Release originalEntity;

    @Before
    public void before() {
        superModel = new com.andrewd.recordlabel.supermodel.ReleaseSlim();
        entity = new Release();
        originalEntity = new Release();

        Mockito.when(modelTransformer.getRelease(Matchers.eq(superModel))).thenReturn(entity);
    }
    
    @Test
    public void mustTransformModelToEntity() {
        superModel.id = 1;

        // Run
        svc.save(superModel);

        // Verify
        Mockito.verify(modelTransformer, times(1)).getRelease(Matchers.eq(superModel));
    }

    @Test
    public void mustGetOriginalEntityFromTheDb_whenModelIsNotNew() {
        int id = 1;
        superModel.id = id;
        entity.id = id;

        originalEntity.images.add(new Image());

        Mockito.when(modelTransformer.getRelease(Matchers.eq(superModel))).thenReturn(entity);
        Mockito.when(repository.getRelease(Matchers.anyInt())).thenReturn(entity);

        // Run
        svc.save(superModel);

        // Verify
        Mockito.verify(repository, times(1)).getRelease(Matchers.eq(id));
    }

    @Test
    public void mustNOTGetOriginalEntityFromTheDb_whenModelIsNew() {
        int id = 0;
        superModel.id = id;
        entity.id = id;

        originalEntity.images.add(new Image());

        Mockito.when(modelTransformer.getRelease(Matchers.eq(superModel))).thenReturn(entity);
        Mockito.when(repository.getRelease(Matchers.anyInt())).thenReturn(entity);

        // Run
        svc.save(superModel);

        // Verify
        Mockito.verify(repository, times(0)).getRelease(Matchers.eq(id));
    }

    @Test
    public void mustAssignTheEntityImagesCollectionFromOriginalModel_whenModelIsNotNew() {
        int id = 1;
        superModel.id = id;
        entity.id = id;

        originalEntity.images.add(new Image());
        List<Image> images = originalEntity.images;

        Mockito.when(modelTransformer.getRelease(Matchers.eq(superModel))).thenReturn(entity);
        Mockito.when(repository.getRelease(Matchers.anyInt())).thenReturn(originalEntity);

        // Capture images field value of the Release argument passed to repository.save
        List<List<Image>> actualImages = new ArrayList<>();
        Mockito.doAnswer(x -> {
                    Release arg = (Release)x.getArguments()[0];
                    actualImages.add(arg.images);
                    return x;
                }).when(repository).save(Matchers.eq(entity));

        // Run
        svc.save(superModel);

        // Verify
        Assert.assertEquals(images, actualImages.get(0));
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void mustThrowException_whenModelIsNotNew_ButOriginalEntityDoesNotExist() {
        int id = 1;
        superModel.id = id;
        entity.id = id;

        originalEntity.images.add(new Image());

        Mockito.when(modelTransformer.getRelease(Matchers.eq(superModel))).thenReturn(entity);

        // Run
        svc.save(superModel);
    }

    @Test
    public void mustSetImagesCollectionValueToNull_whenModelIsNew() {
        int id = 0;
        superModel.id = id;
        entity.id = id;

        originalEntity.images.add(new Image());

        Mockito.when(modelTransformer.getRelease(Matchers.eq(superModel))).thenReturn(entity);
        Mockito.when(repository.getRelease(Matchers.anyInt())).thenReturn(originalEntity);

        // Capture images field value of the Release argument passed to repository.save
        List<List<Image>> actualImages = new ArrayList<>();
        Mockito.doAnswer(x -> {
            Release arg = (Release)x.getArguments()[0];
            actualImages.add(arg.images);
            return x;
        }).when(repository).save(Matchers.eq(entity));

        // Run
        svc.save(superModel);

        // Verify
        Assert.assertNull(actualImages.get(0));
    }

    @Test
    public void mustInvokeSaveOnRepository() {
        superModel.id = 1;

        Mockito.doAnswer(x -> entity).when(modelTransformer).getRelease(Matchers.eq(superModel));

        // Run
        svc.save(superModel);

        // Verify
        Mockito.verify(repository, times(1)).save(Matchers.eq(entity));
    }
}