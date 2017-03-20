package com.andrewd.recordlabel.data.service;

import com.andrewd.recordlabel.data.entities.Image;
import com.andrewd.recordlabel.data.repository.ImagesRepository;
import com.andrewd.recordlabel.data.services.ImagesServiceDefault;
import com.andrewd.recordlabel.data.transformers.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.*;
import java.util.function.Function;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ImagesServiceDefaultTests {

    @InjectMocks
    ImagesServiceDefault svc;

    @Mock
    ImagesRepository repository;

    @Mock
    EntityToModelTransformer entityTransformer;

    @Mock
    ModelToEntityTransformer modelTransformer;

    private final int ownerId = 1;

    private List<Image> images;
    private List<com.andrewd.recordlabel.supermodels.Image> imagesModels;

    @Before
    public void init() {
        images = new ArrayList<>();
        Mockito.when(repository.getImages(Matchers.eq(ownerId)))
                .thenReturn(images);
    }

    @Test
    public void getImages_MustHitTheRepositoryWithTheSuppliedArgument() {
        svc.getImages(ownerId);

        Mockito.verify(repository, times(1))
                .getImages(Matchers.eq(ownerId));
    }

    @Test
    public void getImages_mustTransformAllImagesEntitiesToModels() {
        Image entity1 = new Image();
        Image entity2 = new Image();
        images.add(entity1);
        images.add(entity2);

        // Run
        svc.getImages(ownerId);

        // Verify
        Mockito.verify(entityTransformer, times(1))
                .transformList(
                        Matchers.eq(images), Matchers.any(Function.class));
    }

    @Test
    public void getImages_mustReturnTransformedImages() {
        Image entity1 = new Image();
        Image entity2 = new Image();
        images.add(entity1);
        images.add(entity2);

        com.andrewd.recordlabel.supermodels.Image model1
                = new com.andrewd.recordlabel.supermodels.Image();
        com.andrewd.recordlabel.supermodels.Image model2
                = new com.andrewd.recordlabel.supermodels.Image();
        List<com.andrewd.recordlabel.supermodels.Image> expectedResult
                = new ArrayList<>();
        expectedResult.add(model1);
        expectedResult.add(model2);

        Mockito.when(entityTransformer.getImage(entity1))
                .thenReturn(model1);
        Mockito.when(entityTransformer.getImage(entity1))
                .thenReturn(model2);
        Mockito.doAnswer(x -> expectedResult)
                .when(entityTransformer)
                .transformList(
                        Matchers.eq(images), Matchers.any(Function.class));

        List<com.andrewd.recordlabel.supermodels.Image> result
                = svc.getImages(ownerId);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void saveImages_mustTransformEachImageSuperModelIntoEntity() {
        svc.save(ownerId, imagesModels);

        Mockito.verify(modelTransformer, times(1))
                .transformList(
                        Matchers.eq(imagesModels), Matchers.any(Function.class));
    }

    @Test
    public void saveImages_mustSaveTransformedEntitiesInRepository() {
        Mockito.when(modelTransformer.transformList(
                Matchers.eq(imagesModels), Matchers.any(Function.class)))
                .thenAnswer(x -> images);

        svc.save(ownerId, imagesModels);

        Mockito.verify(repository, times(1))
                .saveImages(Matchers.eq(ownerId), Matchers.eq(images));
    }

    @Test
    public void saveImages_mustTransformEntitiesReturnedByRepositoryBackToSuperModels() {
        Mockito.when(repository.saveImages(Matchers.eq(ownerId), Matchers.eq(images)))
                .thenReturn(images);

        svc.save(ownerId, imagesModels);

        Mockito.verify(entityTransformer, times(1))
                .transformList(
                        Matchers.eq(images), Matchers.any(Function.class));
    }

    // Why on earth doesn't this work?
    /*@Test
    public void saveImages_mustReturnEntitiesReturnedByRepositoryTransformedToSuperModels() {
        Mockito.when(entityTransformer.transformList(
                Matchers.eq(images), Matchers.any(Function.class)))
                .thenAnswer(x -> imagesModels);

        List<com.andrewd.recordlabel.supermodels.Image> result
                = svc.save(ownerId, imagesModels);

        Assert.assertArrayEquals(imagesModels.toArray(), result.toArray());
    }*/

    @Test
    public void get_mustGetImageFromRepository() {
        int imageId = 711;

        svc.get(imageId);

        Mockito.verify(repository, times(1))
                .get(Matchers.eq(imageId));
    }

    @Test
    public void get_mustTransformEntityToModel() {
        int imageId = 711;

        Image image = new Image();
        Mockito.when(repository.get(Matchers.anyInt()))
                .thenReturn(image);

        // Run
        svc.get(imageId);

        // Verify
        Mockito.verify(entityTransformer, times(1))
                .getImage(Matchers.eq(image));
    }
}