package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.data.EntityDoesNotExistException;
import com.andrewd.recordlabel.data.services.ImagesService;
import com.andrewd.recordlabel.image.resize.ImageResizer;
import com.andrewd.recordlabel.io.*;
import com.andrewd.recordlabel.supermodels.Image;
import com.andrewd.recordlabel.web.components.UriBuilder;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import java.io.*;
import java.util.function.Function;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ThumbnailsControllerTests {

    @InjectMocks
    ThumbnailsController controller;

    @Mock
    ImagesService imageSvc;

    @Mock
    Function<String, File> fileFactory;

    @Mock
    RandomFileCreator randomFileCreator;

    @Mock
    IOFunction<File, FileInputStream> fileInputStreamFactory;

    @Mock
    IOFunction<File, FileOutputStream> fileOutputStreamFactory;

    @Mock
    ImageResizer imageResizer;

    @Mock
    UriBuilder uriBuilder;

    @Mock
    FileExtensionGetter fileExtensionGetter;

    private final String thumbsPhysicalPath = "/thumbs/";
    private final String thumbFileNamePrefix = "tmb";
    private final String thumbFileName = "thumb.jpg";
    private final int thumbWidth = 150;
    private final int objectId = 1;
    private final int imageId = 2;
    private final String imageFileName = "image.jpg";
    private final String fullImagePath = "img/image.jpg";
    private final String extension = "jpg";

    private Image image;
    private File imageFile;
    private File thumbFile;
    private File thumbsDirectory;
    private FileInputStream imageFileInputStream;
    private FileOutputStream thumbFileOutputStream;

    @Before
    public void before() throws IOException {
        image = new Image(imageFileName);
        imageFile = new File(fullImagePath);
        thumbFile = Mockito.mock(File.class);
        thumbsDirectory = new File(thumbsPhysicalPath);

        thumbFileOutputStream = Mockito.mock(FileOutputStream.class);
        imageFileInputStream = Mockito.mock(FileInputStream.class);

        controller.thumbsPhysicalPath = thumbsPhysicalPath;
        controller.thumbFileNamePrefix = thumbFileNamePrefix;
        controller.thumbWidth = thumbWidth;

        Mockito.when(thumbFile
                .getName())
                .thenReturn(thumbFileName);

        Mockito.when(imageSvc
                .get(
                        Matchers.anyInt()))
                .thenReturn(image);

        Mockito.when(uriBuilder
                .build(
                        Matchers.anyString(),
                        Matchers.anyString()))
                .thenReturn(fullImagePath);

        Mockito.when(fileFactory
                .apply(Matchers.eq(fullImagePath)))
                .thenReturn(imageFile);

        Mockito.when(fileFactory
                .apply(Matchers.eq(thumbsPhysicalPath)))
                .thenReturn(thumbsDirectory);

        Mockito.when(fileExtensionGetter
                .getFileExtension(
                        Matchers.eq(imageFileName),
                        Matchers.eq(false)))
                .thenReturn(extension);

        Mockito.when(fileInputStreamFactory
                .apply(Matchers.any(File.class)))
                .thenReturn(imageFileInputStream);

        Mockito.when(randomFileCreator
                .createFile(
                        Matchers.anyString(),
                        Matchers.anyString(),
                        Matchers.any(File.class)))
                .thenReturn(thumbFile);

        Mockito.when(fileOutputStreamFactory
                .apply(Matchers.any(File.class)))
                .thenReturn(thumbFileOutputStream);
    }

    @Test
    public void get_mustRetrieveImageFromTheMetadatastore() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(imageSvc, times(1))
                .get(Matchers.eq(imageId));
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void get_whenImageDoesNotExist_mustThrowException() throws Exception {
        Mockito.when(imageSvc
                .get(
                        Matchers.anyInt()))
                .thenReturn(null);

        controller.create(objectId, imageId);
    }

    @Test
    public void get_mustGetFullPathToImage() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(uriBuilder, times(1))
                .build(
                        Matchers.eq(thumbsPhysicalPath),
                        Matchers.eq(imageFileName));
    }

    @Test
    public void get_mustReadImageFile() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(fileFactory, times(1))
                .apply(Matchers.eq(fullImagePath));
    }

    @Test
    public void get_mustGetImageFileAsFileInputStream() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(fileInputStreamFactory, times(1))
                .apply(Matchers.eq(imageFile));
    }

    @Test
    public void get_mustGetFileExtension() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(fileExtensionGetter, times(1))
                .getFileExtension(
                        Matchers.eq(imageFileName),
                        Matchers.eq(false));
    }

    @Test
    public void get_mustGetPhysicalThumbsDirectory() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(fileFactory, times(1))
                .apply(Matchers.eq(thumbsPhysicalPath));
    }

    @Test
    public void get_mustCreateThumbnailFile() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(randomFileCreator, times(1))
                .createFile(
                        Matchers.eq(thumbFileNamePrefix),
                        Matchers.eq(extension),
                        Matchers.eq(thumbsDirectory));
    }

    @Test
    public void get_mustGetThumbnailFileOutputStream() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(fileOutputStreamFactory, times(1))
                .apply(Matchers.eq(thumbFile));
    }

    @Test
    public void get_mustResizeImage() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(imageResizer, times(1))
                .resizeImage(
                        Matchers.eq(imageFileInputStream),
                        Matchers.eq(thumbFileOutputStream),
                        Matchers.eq(extension),
                        Matchers.eq(thumbWidth));
    }

    @Test
    public void get_mustCloseFileInputStream() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(imageFileInputStream, times(1))
                .close();
    }

    @Test
    public void get_mustCloseTheOutputStream() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(thumbFileOutputStream, times(1))
                .close();
    }

    @Test
    public void get_mustSaveThumbnailToTheMetadatastore() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(imageSvc, times(1))
                .saveThumbnail(
                        Matchers.eq(objectId),
                        Matchers.eq(thumbFileName));
    }

    @Test
    public void get_inCaseOfExceptionResizingImage_mustCloseInputAndOutputStreams()
            throws Exception {

        setResizerToThrowException();

        try {
            controller.create(objectId, imageId);
        } catch(Exception e) {}

        Mockito.verify(thumbFileOutputStream)
                .close();

        Mockito.verify(imageFileInputStream)
                .close();
    }

    @Test
    public void get_inCaseOfExceptionResizingImage_mustDeleteThumbnailFile()
            throws Exception {

        setResizerToThrowException();

        testThumbFileDeletion();
    }

    @Test(expected = Exception.class)
    public void get_inCaseOfExceptionResizingImage_mustRethrowException()
            throws Exception {

        setResizerToThrowException();

        controller.create(objectId, imageId);
    }

    @Test
    public void get_inCaseOfExceptionSavingToTheMetadatastore_mustDeleteThumbnailFile()
            throws Exception {

        setServiceSaveThumbnailToThrowException();

        testThumbFileDeletion();
    }

    @Test(expected = Exception.class)
    public void get_inCaseOfExceptionSavingToTheMetadatastore_mustRethrowException()
            throws Exception {

        setServiceSaveThumbnailToThrowException();

        controller.create(objectId, imageId);
    }

    private void testThumbFileDeletion() {
        try {
            controller.create(objectId, imageId);
        } catch(Exception e) {}

        Mockito.verify(thumbFile, times(1)).delete();
    }

    private void setResizerToThrowException() throws Exception {
        Mockito.doThrow(Exception.class)
                .when(imageResizer)
                .resizeImage(
                        Matchers.any(InputStream.class),
                        Matchers.any(OutputStream.class),
                        Matchers.anyString(),
                        Matchers.anyInt());
    }

    private void setServiceSaveThumbnailToThrowException() throws Exception {
        Mockito.doThrow(Exception.class)
                .when(imageSvc)
                .saveThumbnail(
                        Matchers.anyInt(),
                        Matchers.anyString());
    }

    // TODO: might want to verify that streams are closed AFTER resizing and BEFORE saving to the database
    // TODO: check if object already contains a thumbnail
    // TODO: delete preexisting thumbnail if exists - only after successful saving of the new one
}