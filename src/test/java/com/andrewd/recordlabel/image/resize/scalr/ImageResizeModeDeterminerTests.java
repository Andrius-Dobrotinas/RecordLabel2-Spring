package com.andrewd.recordlabel.image.resize.scalr;

import com.andrewd.recordlabel.image.resize.scalr.ImageResizeModeDeterminer;
import org.imgscalr.Scalr;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import java.awt.image.BufferedImage;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ImageResizeModeDeterminerTests {

    @InjectMocks
    ImageResizeModeDeterminer component;

    BufferedImage image;

    private final int width = 100;

    @Before
    public void init() {
        image = Mockito.mock(BufferedImage.class);

        Mockito.when(image.getWidth())
                .thenReturn(width);
    }

    @Test
    public void mustGetImageWidth() {
       component.apply(image);

        Mockito.verify(image, times(1)).getWidth();
    }

    @Test
    public void mustGetImageHeight() {
        component.apply(image);

        Mockito.verify(image, times(1)).getHeight();
    }

    @Test
    public void whenHeightIsGreaterThanWidth_mustReturn_FIT_TO_WIDTH() {
        Mockito.when(image.getHeight())
                .thenReturn(width*2);

        Scalr.Mode result = component.apply(image);

        Assert.assertEquals(Scalr.Mode.FIT_TO_HEIGHT, result);
    }

    @Test
    public void whenHeightIsLessThanWidth_mustReturn_FIT_TO_WIDTH() {
        Mockito.when(image.getHeight())
                .thenReturn(width/2);

        Scalr.Mode result = component.apply(image);

        Assert.assertEquals(Scalr.Mode.FIT_TO_WIDTH, result);
    }

    @Test
    public void whenHeightAndWithAreTheSame_mustReturn_FIT_EXACT() {
        Mockito.when(image.getHeight())
                .thenReturn(width);

        Scalr.Mode result = component.apply(image);

        Assert.assertEquals(Scalr.Mode.FIT_EXACT, result);
    }
}