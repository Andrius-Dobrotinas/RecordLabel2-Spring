package com.andrewd.recordlabel.web.components;

import com.andrewd.recordlabel.supermodels.Image;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ImageFilenameUrlifierDefaultTests {

    @InjectMocks
    ImageFilenameUrifierDefault urifier;

    @Mock
    UriBuilderFunction uriBuilder;

    private Image image;

    private final String name1 = "name 1";
    private final String path = "/imgs/";

    @Before
    public void init() {
        image = new Image(name1);
    }

    @Test
    public void mustBuildUrlForImage() {
        urifier.urlify(image, path);

        Mockito.verify(uriBuilder, times(1))
                .build(
                        Matchers.eq(path),
                        Matchers.eq(name1));
    }

    @Test
    public void mustChangeImageFilenameToUrl() {
        String expectedName1 = "expected name1";

        Mockito.when(uriBuilder
                .build(
                        Matchers.anyString(),
                        Matchers.eq(name1)))
                .thenReturn(expectedName1);

        // Run
        urifier.urlify(image, path);

        // Verify
        Assert.assertEquals(expectedName1, image.fileName);
    }
}