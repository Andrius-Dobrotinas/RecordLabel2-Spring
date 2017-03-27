package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.data.EntityDoesNotExistException;
import com.andrewd.recordlabel.data.services.ImagesService;
import com.andrewd.recordlabel.supermodels.Image;
import com.andrewd.recordlabel.web.components.ThumbnailCreator;
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
public class ThumbnailsControllerTests {

    @InjectMocks
    ThumbnailsController controller;

    @Mock
    ImagesService imageSvc;

    @Mock
    ThumbnailCreator thumbnailCreator;

    private final int imageId = 2;
    private Image sourceImage;

    @Before
    public void init() {
        sourceImage = new Image();
        sourceImage.id = imageId;

        Mockito.when(imageSvc
                .get(
                        Matchers.eq(imageId)))
                .thenReturn(sourceImage);
    }

    @Test
    public void create_mustRetrieveImageFromTheMetadatastore() throws Exception {
        controller.create(imageId);

        Mockito.verify(imageSvc, times(1))
                .get(Matchers.eq(imageId));
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void create_whenImageDoesNotExist_mustThrowException() throws Exception {
        controller.create(0);
    }

    @Test
    public void create_mustMustThumbnailCreatorToCreateThumbnail() throws Exception {
        controller.create(imageId);

        Mockito.verify(thumbnailCreator, times(1))
                .createThumbnail(Matchers.eq(sourceImage));
    }
}