package com.andrewd.recordlabel.service;

import com.andrewd.recordlabel.common.BatchedResult;
import com.andrewd.recordlabel.common.service.BatchCountCalculator;
import com.andrewd.recordlabel.data.model.*;
import com.andrewd.recordlabel.data.repository.ReleaseRepository;
import com.andrewd.recordlabel.data.service.EntityToModelTransformer;
import com.andrewd.recordlabel.data.service.ModelToEntityTransformer;
import com.andrewd.recordlabel.data.service.ReleaseServiceDefault;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;
import java.util.function.Function;

@RunWith(MockitoJUnitRunner.class)
public class ReleaseServiceDefaultTests {

    @InjectMocks
    ReleaseServiceDefault svc = new ReleaseServiceDefault();

    @Mock
    ReleaseRepository repository;

    @Mock
    EntityToModelTransformer entityTransformer;

    @Mock
    ModelToEntityTransformer modelTransformer;

    @Test
    public void getRelease_MustReturnSuperModel() {
        int entityId = 1;
        Release entity = new Release();
        com.andrewd.recordlabel.supermodel.Release expectedModel =
                new com.andrewd.recordlabel.supermodel.Release();

        Mockito.when(repository.getRelease(Matchers.anyInt())).thenReturn(entity);
        Mockito.when(entityTransformer.getRelease(Matchers.any(Release.class))).thenReturn(expectedModel);

        // Run
        com.andrewd.recordlabel.supermodel.Release result = svc.getRelease(entityId);

        // Verify
        Mockito.verify(entityTransformer, Mockito.times(1)).getRelease(entity);
        Assert.assertEquals("A transformed release model must be returned", expectedModel, result);
    }

    @Test
    public void getRelease_MustReturnNullWhenNotFound() {
        int entityId = 1;
        Release entity = new Release();
        Mockito.when(repository.getRelease(Matchers.anyInt())).thenReturn(entity);

        // Run
        com.andrewd.recordlabel.supermodel.Release model = svc.getRelease(entityId);

        Assert.assertNull("Null must be returned in case no release is found", model);
    }

    @Test
    public void getReleases() {
        List<Release> entites = new ArrayList<>();
        int totalCount = 6;
        int batchNumber = 1;
        int batchSize = 2;

        List<com.andrewd.recordlabel.supermodel.Release> superModels = new ArrayList<>();
        com.andrewd.recordlabel.supermodel.Release superModel1 = new com.andrewd.recordlabel.supermodel.Release();
        com.andrewd.recordlabel.supermodel.Release superModel2 = new com.andrewd.recordlabel.supermodel.Release();
        superModels.add(superModel1);
        superModels.add(superModel2);

        Mockito.when(repository.getAllReleases()).thenReturn(entites);
        Mockito.when(repository.getTotalReleaseCount()).thenReturn(totalCount);
        Mockito.doAnswer(x -> superModels)
                .when(entityTransformer)
                .transformList(Matchers.eq(entites), Matchers.any(Function.class));

        BatchedResult<?> result = svc.getReleases(batchNumber, batchSize);

        Assert.assertEquals("Must include transformed entries", superModels, result.entries);
        Assert.assertEquals("Must include calculated total batch count",BatchCountCalculator.calc(totalCount, batchSize), result.batchCount);
    }

    @Test
    public void getMediaTypeList_MustHitTheRepositoryAndReturnSuperModel() {
        List<MediaType> entities = new ArrayList<>();
        entities.add(new MediaType());

        List<com.andrewd.recordlabel.supermodel.MediaType> fake = new ArrayList<>();
        fake.add(new com.andrewd.recordlabel.supermodel.MediaType());

        Mockito.when(repository.getMediaTypeList()).thenReturn(entities);
        Mockito.doAnswer(invocation -> {return fake;}).when(entityTransformer)
                .transformList(Matchers.eq(entities), Matchers.any(Function.class));

        // Run
        List<com.andrewd.recordlabel.supermodel.MediaType> result = svc.getMediaTypeList();

        // Verify
        Mockito.verify(repository, Mockito.times(1)).getMediaTypeList();
        Mockito.verify(entityTransformer, Mockito.times(1))
                .transformList(Mockito.eq(entities), Mockito.any(Function.class));
        Assert.assertNotNull("Result cannot ever be null", result);
        Assert.assertEquals("Result must match what is returned by the service", fake, result);
    }

    @Test
    public void getMetadataList_MustHitTheRepositoryAndReturnSuperModel() {
        List<Metadata> entities = new ArrayList<>();
        entities.add(new Metadata());

        List<com.andrewd.recordlabel.supermodel.Metadata> fake = new ArrayList<>();
        fake.add(new com.andrewd.recordlabel.supermodel.Metadata());

        Mockito.when(repository.getMetadataList()).thenReturn(entities);
        Mockito.doAnswer(invocation -> {return fake;}).when(entityTransformer)
                .transformList(Matchers.eq(entities), Matchers.any(Function.class));

        // Run
        List<com.andrewd.recordlabel.supermodel.Metadata> result = svc.getMetadataList();

        // Verify
        Mockito.verify(repository, Mockito.times(1)).getMetadataList();
        Mockito.verify(entityTransformer, Mockito.times(1))
                .transformList(Mockito.eq(entities), Mockito.any(Function.class));
        Assert.assertNotNull("Result cannot ever be null", result);
        Assert.assertEquals("Result must match what is returned by the service", fake, result);
    }
}