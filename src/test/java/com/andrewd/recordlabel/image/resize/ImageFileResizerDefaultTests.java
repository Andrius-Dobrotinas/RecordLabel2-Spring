package com.andrewd.recordlabel.image.resize;

import com.andrewd.recordlabel.io.IOFunction;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import java.io.*;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ImageFileResizerDefaultTests {

    @InjectMocks
    ImageFileResizerDefault resizer;

    @Mock
    IOFunction<File, FileInputStream> fileInputStreamFactory;

    @Mock
    IOFunction<File, FileOutputStream> fileOutputStreamFactory;

    @Mock
    ImageResizer imageResizer;

    private final int targetSize = 150;
    private final String imageFormatName = "jpg";
    private File source;
    private File destination;
    private FileInputStream inputStream;
    private FileOutputStream outputStream;

    @Before
    public void init() throws IOException {
        source = Mockito.mock(File.class);
        destination = Mockito.mock(File.class);
        outputStream = Mockito.mock(FileOutputStream.class);
        inputStream = Mockito.mock(FileInputStream.class);

        Mockito.when(fileInputStreamFactory
                .apply(Matchers.any(File.class)))
                .thenReturn(inputStream);

        Mockito.when(fileOutputStreamFactory
                .apply(Matchers.any(File.class)))
                .thenReturn(outputStream);
    }

    @Test
    public void mustGetSourceImageFileAsFileInputStream() throws Exception {
        resizer.resize(source, destination, imageFormatName, targetSize);

        Mockito.verify(fileInputStreamFactory, times(1))
                .apply(Matchers.eq(source));
    }

    @Test
    public void mustGetDestinationImageFileAsFileOutputStream() throws Exception {
        resizer.resize(source, destination, imageFormatName, targetSize);

        Mockito.verify(fileOutputStreamFactory, times(1))
                .apply(Matchers.eq(destination));
    }

    @Test
    public void mustResizeImage() throws Exception {
        resizer.resize(source, destination, imageFormatName, targetSize);

        Mockito.verify(imageResizer, times(1))
                .resizeImage(
                        Matchers.eq(inputStream),
                        Matchers.eq(outputStream),
                        Matchers.eq(imageFormatName),
                        Matchers.eq(targetSize));
    }

    @Test
    public void mustCloseTheInputStream() throws Exception {
        resizer.resize(source, destination, imageFormatName, targetSize);

        Mockito.verify(inputStream, times(1))
                .close();
    }

    @Test
    public void mustCloseTheOutputStream() throws Exception {
        resizer.resize(source, destination, imageFormatName, targetSize);

        Mockito.verify(outputStream, times(1))
                .close();
    }

    @Test
    public void inCaseOfExceptionOpeningOutputStream_mustCloseInputStream()
            throws Exception {

        Mockito.doThrow(Exception.class)
                .when(fileOutputStreamFactory)
                .apply(Matchers.eq(destination));

        try {
            resizer.resize(source, destination, imageFormatName, targetSize);
        } catch(Exception e) {}

        Mockito.verify(inputStream)
                .close();
    }

    @Test
    public void inCaseOfExceptionResizingImage_mustCloseInputAndOutputStreams()
            throws Exception {

        Mockito.doThrow(Exception.class)
                .when(imageResizer)
                .resizeImage(
                        Matchers.any(InputStream.class),
                        Matchers.any(OutputStream.class),
                        Matchers.anyString(),
                        Matchers.anyInt());

        try {
            resizer.resize(source, destination, imageFormatName, targetSize);
        } catch(Exception e) {}

        Mockito.verify(outputStream)
                .close();

        Mockito.verify(inputStream)
                .close();
    }
}