package com.andrewd.recordlabel.image.resize.scalr;

import com.andrewd.recordlabel.image.*;
import com.andrewd.recordlabel.image.resize.scalr.ImageResizerDefault;
import com.andrewd.recordlabel.image.resize.scalr.ScalrResizer;
import com.andrewd.recordlabel.io.IOFunction;
import org.imgscalr.Scalr;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.function.Function;

@RunWith(MockitoJUnitRunner.class)
public class ImageResizerTests {

    @InjectMocks
    ImageResizerDefault component;

    @Mock
    IOFunction<InputStream, BufferedImage> bufferedImageFactory;

    @Mock
    Function<BufferedImage, Scalr.Mode> resizeModeDeterminer;

    @Mock
    ScalrResizer scalrResizer;

    @Mock
    RenderedImageWriter imageWriter;

    private final Scalr.Method scalingMethod = Scalr.Method.QUALITY;
    private final Scalr.Mode resizeMode = Scalr.Mode.FIT_EXACT;
    private final String imageFormatName = "jpg";
    private final int targetSize = 100;

    private InputStream imageInputStream;
    private OutputStream resizedImageOutputStream;
    private BufferedImage origImage;
    private BufferedImage resizedImage;

    @Before
    public void init() throws IOException {
        component.scalingMethod = scalingMethod;

        imageInputStream = Mockito.mock(InputStream.class);
        resizedImageOutputStream = Mockito.mock(OutputStream.class);
        origImage = Mockito.mock(BufferedImage.class);
        resizedImage = Mockito.mock(BufferedImage.class);

        Mockito.when(bufferedImageFactory
                .apply(Matchers.eq(imageInputStream)))
                .thenReturn(origImage);

        Mockito.when(resizeModeDeterminer
                .apply(Matchers.any()))
                .thenReturn(resizeMode);

        Mockito.when(scalrResizer
                .resize(
                        Matchers.any(BufferedImage.class),
                        Matchers.anyString(),
                        Matchers.anyInt(),
                        Matchers.any(Scalr.Method.class),
                        Matchers.any(Scalr.Mode.class)))
                .thenReturn(resizedImage);
    }

    @Test
    public void mustGetBufferedImage() throws Exception {
        component.resizeImage(imageInputStream, resizedImageOutputStream,
                imageFormatName, targetSize);

        Mockito.verify(bufferedImageFactory, Mockito.times(1))
                .apply(Matchers.eq(imageInputStream));
    }

    @Test
    public void mustDetermineResizeMode() throws Exception {
        component.resizeImage(imageInputStream, resizedImageOutputStream,
                imageFormatName, targetSize);

        Mockito.verify(resizeModeDeterminer, Mockito.times(1))
                .apply(Matchers.eq(origImage));
    }

    @Test
    public void mustResizeImage() throws Exception {
        component.resizeImage(imageInputStream, resizedImageOutputStream,
                imageFormatName, targetSize);

        Mockito.verify(scalrResizer, Mockito.times(1))
                .resize(
                        Matchers.eq(origImage),
                        Matchers.eq(imageFormatName),
                        Matchers.eq(targetSize),
                        Matchers.eq(scalingMethod),
                        Matchers.eq(resizeMode));
    }

    @Test
    public void mustWriteResizedImageToTheOutputStream() throws Exception {
        component.resizeImage(imageInputStream, resizedImageOutputStream,
                imageFormatName, targetSize);

        Mockito.verify(imageWriter, Mockito.times(1))
                .write(
                        Matchers.eq(resizedImage),
                        Matchers.eq(imageFormatName),
                        Matchers.eq(resizedImageOutputStream));
    }
}