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
    public UrlBuilderFunction urlBuilder;

    final String imgVirtualPath = "/img/";
    final int ownerId = 1;

    List<Image> images;

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
    public void get_mustBuildUrlsForEachImage() {
        String name1 = "cover1.jpg";
        String name2 = "cover2.png";
        images.add(getImage(name1));
        images.add(getImage(name2));

        // Run
        controller.get(ownerId);

        // Verify
        Mockito.verify(urlBuilder, times(1))
                .build(Matchers.eq(imgVirtualPath), Matchers.eq(name1));
        Mockito.verify(urlBuilder, times(1))
                .build(Matchers.eq(imgVirtualPath), Matchers.eq(name2));
    }

    /* This might not seem exactly right, but these instances of Image objects will
     not be used anymore anyway
      */
    @Test
    public void get_mustAssignUrlToEachImagesFileNameField() {
        String name1 = "cover1.jpg";
        String name2 = "cover2.png";
        String expectedName1 = "expected name1";
        String expectedName2 = "expected name2";
        Image image1 = getImage(name1);
        Image image2 = getImage(name2);
        images.add(image1);
        images.add(image2);

        Mockito.when(urlBuilder.build(Matchers.anyString(), Matchers.eq(name1)))
                .thenReturn(expectedName1);
        Mockito.when(urlBuilder.build(Matchers.anyString(), Matchers.eq(name2)))
                .thenReturn(expectedName2);

        // Run
        controller.get(ownerId);

        // Verify
        Assert.assertEquals(expectedName1, image1.fileName);
        Assert.assertEquals(expectedName2, image2.fileName);
    }

    private Image getImage(String name) {
        Image image = new Image();
        image.fileName = name;
        return image;
    }
}