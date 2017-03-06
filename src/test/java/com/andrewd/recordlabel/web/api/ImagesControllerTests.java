package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.data.services.ImagesService;
import com.andrewd.recordlabel.supermodels.Image;
import com.andrewd.recordlabel.web.components.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.*;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ImagesControllerTests {

    @InjectMocks
    ImagesController controller;

    @Mock
    ImagesService svc;

    @Mock
    ImageFilenameUrlifier imgUrlifier;

    private final String imgVirtualPath = "/img/";
    private final int ownerId = 1;

    private List<Image> images;

    @Before
    public void before() {
        controller.imagesVirtualPath = imgVirtualPath;
        images = new ArrayList<>();

        Mockito.when(svc.getImages(Matchers.anyInt()))
                .thenReturn(images);
    }

    @Test
    public void get_mustRetrieveImagesFromTheService() {
        List<Image> result = controller.get(ownerId);

        Mockito.verify(svc, times(1))
                .getImages(Matchers.eq(ownerId));
    }

    @Test
    public void get_mustReturnListReturnedByTheService() {
        List<Image> result = controller.get(ownerId);

        Assert.assertArrayEquals(images.toArray(), result.toArray());
    }

    @Test
    public void get_mustUseUrlifierToChangeEachImagesFileNameToAUrl() {
        String name1 = "cover1.jpg";
        String name2 = "cover2.png";
        Image image1 = new Image(name1);
        Image image2 = new Image(name2);
        images.add(image1);
        images.add(image2);

        // Run
        controller.get(ownerId);

        Mockito.verify(imgUrlifier, times(1))
                .urlify(Matchers.eq(image1), Matchers.eq(imgVirtualPath));
        Mockito.verify(imgUrlifier, times(1))
                .urlify(Matchers.eq(image2), Matchers.eq(imgVirtualPath));
    }
}