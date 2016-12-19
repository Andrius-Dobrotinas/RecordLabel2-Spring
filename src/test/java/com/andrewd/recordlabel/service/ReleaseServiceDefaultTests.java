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

import static org.mockito.Mockito.times;

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
    public void getRelease_MustHitTheRepositoryWithTheSuppliedArgument() {
        int entityId = 1;

        // Run
        svc.getRelease(entityId);

        // Verify
        Mockito.verify(repository, times(1)).getRelease(Matchers.eq(entityId));
    }

    @Test
    public void getRelease_TransformEntityToModel() {
        int entityId = 1;
        Release entity = new Release();

        Mockito.when(repository.getRelease(Matchers.eq(entityId))).thenReturn(entity);

        // Run
        svc.getRelease(entityId);

        // Verify
        Mockito.verify(entityTransformer, times(1)).getRelease(Matchers.eq(entity));
    }

    @Test
    public void getRelease_MustReturnSuperModel() {
        int entityId = 1;
        Release entity = new Release();
        com.andrewd.recordlabel.supermodel.Release expectedModel =
                new com.andrewd.recordlabel.supermodel.Release();

        Mockito.when(repository.getRelease(Matchers.eq(entityId))).thenReturn(entity);
        Mockito.when(entityTransformer.getRelease(Matchers.eq(entity))).thenReturn(expectedModel);

        // Run
        com.andrewd.recordlabel.supermodel.Release result = svc.getRelease(entityId);

        // Verify
        Assert.assertEquals("A transformed release model must be returned", expectedModel, result);
    }

    @Test
    public void getRelease_MustReturnNullWhenNotFound() {
        int entityId = 1;
        Release entity = new Release();
        Mockito.when(repository.getRelease(Matchers.eq(entityId))).thenReturn(entity);

        // Run
        com.andrewd.recordlabel.supermodel.Release model = svc.getRelease(entityId);

        // Verify
        Assert.assertNull("Null must be returned in case no release is found", model);
    }

    @Test
    public void getReleaseSlim_MustHitTheRepositoryWithTheSuppliedArgument() {
        int entityId = 1;

        // Run
        svc.getReleaseSlim(entityId);

        // Verify
        Mockito.verify(repository, times(1)).getRelease(Matchers.eq(entityId));
    }

    @Test
    public void getReleaseSlim_MustTransformEntityToModel() {
        int entityId = 1;
        Release entity = new Release();
        Mockito.when(repository.getRelease(Matchers.eq(entityId))).thenReturn(entity);

        // Run
        svc.getReleaseSlim(entityId);

        // Verify
        Mockito.verify(entityTransformer, times(1)).getReleaseSlim(Matchers.eq(entity));
    }

    @Test
    public void getReleaseSlim_MustReturnSuperModel() {
        int entityId = 1;
        Release entity = new Release();
        com.andrewd.recordlabel.supermodel.ReleaseSlim expectedModel =
                new com.andrewd.recordlabel.supermodel.ReleaseSlim();

        Mockito.when(repository.getRelease(Matchers.eq(entityId))).thenReturn(entity);
        Mockito.when(entityTransformer.getReleaseSlim(Matchers.eq(entity))).thenReturn(expectedModel);

        // Run
        com.andrewd.recordlabel.supermodel.ReleaseSlim result = svc.getReleaseSlim(entityId);

        // Verify
        Assert.assertEquals("A transformed release model must be returned", expectedModel, result);
    }

    @Test
    public void getReleaseSlim_MustReturnNullWhenNotFound() {
        int entityId = 1;
        Release entity = new Release();
        Mockito.when(repository.getRelease(Matchers.eq(entityId))).thenReturn(entity);

        // Run
        com.andrewd.recordlabel.supermodel.ReleaseSlim model = svc.getReleaseSlim(entityId);

        Assert.assertNull("Null must be returned in case no release is found", model);
    }

    @Test
    public void getReleases_MustHitTheRepositoryWithTheSuppliedArguments() {
        int batchNumber = 1;
        int batchSize = 2;

        // Run
        svc.getReleases(batchNumber, batchSize);

        // Verify
        // TODO: when I actually implement this stuff in the repository, check the arguments
        Mockito.verify(repository, times(1)).getAllReleases();
    }

    @Test
    public void getReleases_MustTransformEntitiesToModels() {
        int batchNumber = 1;
        int batchSize = 2;
        List<Release> entities = new ArrayList<>();

        Mockito.when(repository.getAllReleases()).thenReturn(entities);

        // Run
        svc.getReleases(batchNumber, batchSize);

        // Verify
        Mockito.verify(entityTransformer, times(1)).transformList(Matchers.eq(entities), Matchers.any(Function.class));
    }

    @Test
    public void getReleases_MustRetrieveTotalItemCountFromRepository() {
        int batchNumber = 1;
        int batchSize = 2;

        // Run
        svc.getReleases(batchNumber, batchSize);

        // Verify
        Mockito.verify(repository, times(1)).getTotalReleaseCount();
    }

    @Test
    public void getReleases_MustReturnTotalBatchCountInRepository() {
        int totalCount = 6;
        int batchNumber = 1;
        int batchSize = 2;

        Mockito.when(repository.getTotalReleaseCount()).thenReturn(totalCount);

        // Run
        BatchedResult<?> result = svc.getReleases(batchNumber, batchSize);

        // Verify
        Assert.assertEquals("Must include calculated total batch count for the supplied batch size",
                BatchCountCalculator.calc(totalCount, batchSize), result.batchCount);
    }

    @Test
    public void getReleases_MustReturnAListOfSuperModels() {
        int batchNumber = 1;
        int batchSize = 2;
        List<Release> entities = new ArrayList<>();

        List<com.andrewd.recordlabel.supermodel.Release> superModels = new ArrayList<>();
        com.andrewd.recordlabel.supermodel.Release superModel1 = new com.andrewd.recordlabel.supermodel.Release();
        com.andrewd.recordlabel.supermodel.Release superModel2 = new com.andrewd.recordlabel.supermodel.Release();
        superModels.add(superModel1);
        superModels.add(superModel2);

        Mockito.when(repository.getAllReleases()).thenReturn(entities);

        Mockito.doAnswer(x -> superModels)
                .when(entityTransformer)
                .transformList(Matchers.eq(entities), Matchers.any(Function.class));

        BatchedResult<?> result = svc.getReleases(batchNumber, batchSize);

        Assert.assertEquals("Must include transformed entries", superModels, result.entries);
    }

    @Test
    public void getMediaTypeList_MustHitTheRepository() {
        // Run
        svc.getMediaTypeList();

        // Verify
        Mockito.verify(repository, times(1)).getMediaTypeList();
    }

    @Test
    public void getMediaTypeList_TransformEntitiesToModels() {
        List<MediaType> entities = new ArrayList<>();
        entities.add(new MediaType());

        Mockito.when(repository.getMediaTypeList()).thenReturn(entities);

        // Run
        svc.getMediaTypeList();

        // Verify
        Mockito.verify(entityTransformer, times(1))
                .transformList(Matchers.eq(entities), Matchers.any(Function.class));
    }

    @Test
    public void getMediaTypeList_MustReturnAListOfSuperModels() {
        List<MediaType> entities = new ArrayList<>();
        entities.add(new MediaType());

        List<com.andrewd.recordlabel.supermodel.MediaType> fake = new ArrayList<>();
        fake.add(new com.andrewd.recordlabel.supermodel.MediaType());

        Mockito.when(repository.getMediaTypeList()).thenReturn(entities);

        Mockito.doAnswer(invocation -> fake)
                .when(entityTransformer)
                .transformList(Matchers.eq(entities), Matchers.any(Function.class));

        // Run
        List<com.andrewd.recordlabel.supermodel.MediaType> result = svc.getMediaTypeList();

        // Verify
        Assert.assertNotNull("Result cannot ever be null", result);
        Assert.assertEquals("Result must match what is returned by the service", fake, result);
    }

    @Test
    public void getMetadataList_MustHitTheRepository() {
        // Run
        svc.getMetadataList();

        // Verify
        Mockito.verify(repository, times(1)).getMetadataList();
    }

    @Test
    public void getMetadataList_MustTransformEntitiesToModels() {
        List<Metadata> entities = new ArrayList<>();
        entities.add(new Metadata());

        Mockito.when(repository.getMetadataList()).thenReturn(entities);

        // Run
        svc.getMetadataList();

        // Verify
        Mockito.verify(entityTransformer, times(1))
                .transformList(Mockito.eq(entities), Mockito.any(Function.class));
    }

    @Test
    public void getMetadataList_MustReturnAListOfSuperModels() {
        List<Metadata> entities = new ArrayList<>();
        entities.add(new Metadata());

        List<com.andrewd.recordlabel.supermodel.Metadata> fake = new ArrayList<>();
        fake.add(new com.andrewd.recordlabel.supermodel.Metadata());

        Mockito.when(repository.getMetadataList()).thenReturn(entities);
        Mockito.doAnswer(invocation -> fake).when(entityTransformer)
                .transformList(Matchers.eq(entities), Matchers.any(Function.class));

        // Run
        List<com.andrewd.recordlabel.supermodel.Metadata> result = svc.getMetadataList();

        // Verify
        Assert.assertNotNull("Result cannot ever be null", result);
        Assert.assertEquals("Result must match what is returned by the service", fake, result);
    }

    @Test
    public void getArtistBarebonesList_MustHitTheRepository() {
        svc.getArtistBarebonesList();

        Mockito.verify(repository, times(1)).getAllArtists();
    }

    @Test
    public void getArtistBarebonesList_MustTransformEntitiesToModels() {
        List<Artist> entities = new ArrayList<>();
        entities.add(new Artist());

        Mockito.when(repository.getAllArtists()).thenReturn(entities);

        // Run
        svc.getArtistBarebonesList();

        // Verify
        Mockito.verify(entityTransformer, times(1))
                .transformList(Matchers.eq(entities), Matchers.any(Function.class));
    }

    @Test
    public void getArtistBarebonesList_MustListOfSuperModels() {
        List<Artist> entities = new ArrayList<>();
        entities.add(new Artist());

        List<com.andrewd.recordlabel.supermodel.ArtistBarebones> fake = new ArrayList<>();
        fake.add(new com.andrewd.recordlabel.supermodel.ArtistBarebones());

        Mockito.when(repository.getAllArtists()).thenReturn(entities);
        Mockito.doAnswer(invocation -> fake).when(entityTransformer)
                .transformList(Matchers.eq(entities), Matchers.any(Function.class));

        // Run
        List<com.andrewd.recordlabel.supermodel.ArtistBarebones> result = svc.getArtistBarebonesList();

        // Verify
        Assert.assertNotNull("Result cannot ever be null", result);
        Assert.assertEquals("Result must match what is returned by the service", fake, result);
    }

    @Test
    public void saveReleaseSlim_MustTransformModelToEntity() {
        com.andrewd.recordlabel.supermodel.ReleaseSlim superModel = new com.andrewd.recordlabel.supermodel.ReleaseSlim();
        superModel.id = 1;

        // Run
        svc.save(superModel);

        // Verify
        Mockito.verify(modelTransformer, times(1)).getRelease(Matchers.eq(superModel));
    }

    @Test
    public void saveReleaseSlim_MustTheService() {
        com.andrewd.recordlabel.supermodel.ReleaseSlim superModel = new com.andrewd.recordlabel.supermodel.ReleaseSlim();
        superModel.id = 1;

        Release entity = new Release();

        Mockito.doAnswer(x -> entity).when(modelTransformer).getRelease(Matchers.eq(superModel));

        // Run
        svc.save(superModel);

        // Verify
        Mockito.verify(repository, times(1)).save(Matchers.eq(entity));
    }
}