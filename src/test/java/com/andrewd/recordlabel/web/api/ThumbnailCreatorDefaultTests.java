package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.data.EntityDoesNotExistException;
import com.andrewd.recordlabel.data.services.*;
import com.andrewd.recordlabel.image.resize.ImageFileResizer;
import com.andrewd.recordlabel.io.*;
import com.andrewd.recordlabel.supermodels.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import java.io.*;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ThumbnailCreatorDefaultTests {

    @InjectMocks
    ThumbnailCreatorDefault controller;

    @Mock
    ReleaseService releasesSvc;

    @Mock
    ImagesService imageSvc;

    @Mock
    ThumbnailsService thumbnailsSvc;

    @Mock
    FileFactory fileFactory;

    @Mock
    RandomFileCreator randomFileCreator;

    @Mock
    FileExtensionGetter fileExtensionGetter;

    @Mock
    ImageFileResizer imageFileResizer;

    private final String imagesPhysicalPath = "/img/";
    private final String thumbsPhysicalPath = "/thumbs/";
    private final String thumbFileNamePrefix = "tmb";
    private final String thumbFileName = "thumb.jpg";
    private final int thumbSize = 150;
    private final int objectId = 1;
    private final int imageId = 2;
    private final String imageFileName = "image.jpg";
    private final String fullImagePath = "img/image.jpg";
    private final String extension = "jpg";

    private final String origThumbName = "original thumbnail.png";
    private final int originalThumbId = 7;

    private Image image;
    private File imageFile;
    private File thumbFile;
    private File thumbsDirectory;
    private Thumbnail origThumbnail;
    private File origThumbFile;

    @Before
    public void before() throws Exception {
        image = new Image(imageFileName);
        imageFile = new File(fullImagePath);
        thumbFile = Mockito.mock(File.class);
        thumbsDirectory = new File(thumbsPhysicalPath);
        origThumbnail = Mockito.mock(Thumbnail.class);
        origThumbnail.fileName = origThumbName;
        origThumbnail.id = originalThumbId;
        origThumbFile = Mockito.mock(File.class);

        controller.imagesPhysicalPath = imagesPhysicalPath;
        controller.thumbsPhysicalPath = thumbsPhysicalPath;
        controller.thumbFileNamePrefix = thumbFileNamePrefix;
        controller.thumbSize = thumbSize;

        Mockito.when(thumbFile
                .getName())
                .thenReturn(thumbFileName);

        Mockito.when(imageSvc
                .get(
                        Matchers.eq(imageId)))
                .thenReturn(image);

        Mockito.when(releasesSvc
                .objectExists(
                        Matchers.eq(objectId)))
                .thenReturn(true);

        Mockito.when(fileFactory
                .getFile(
                        Matchers.eq(imagesPhysicalPath),
                        Matchers.eq(imageFileName)))
                .thenReturn(imageFile);

        Mockito.when(fileFactory
                .getFile(Matchers.eq(thumbsPhysicalPath)))
                .thenReturn(thumbsDirectory);

        Mockito.when(fileFactory
                .getFile(
                        Matchers.eq(thumbsPhysicalPath),
                        Matchers.eq(origThumbName)))
                .thenReturn(origThumbFile);

        Mockito.when(fileExtensionGetter
                .getFileExtension(
                        Matchers.eq(imageFileName),
                        Matchers.eq(false)))
                .thenReturn(extension);

        Mockito.when(randomFileCreator
                .createFile(
                        Matchers.anyString(),
                        Matchers.anyString(),
                        Matchers.any(File.class)))
                .thenReturn(thumbFile);
    }

    @Test
    public void get_mustRetrieveImageFromTheMetadatastore() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(imageSvc, times(1))
                .get(Matchers.eq(imageId));
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void get_whenImageDoesNotExist_mustThrowException() throws Exception {
        controller.create(objectId, 0);
    }

    @Test
    public void get_mustCheckIfTargetObjectExists() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(releasesSvc, times(1))
                .objectExists(
                        Matchers.eq(objectId));
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void get_whenTargetObjectDoesNotExist_mustThrowException() throws Exception {
        controller.create(0, imageId);
    }

    @Test
    public void get_mustRetrieveCurrentThumbnailFromTheDatastore() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(thumbnailsSvc, times(1))
                .getByOwner(
                        Matchers.eq(objectId));
    }

    @Test
    public void get_mustReadImageFile() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(fileFactory, times(1))
                .getFile(
                        Matchers.eq(imagesPhysicalPath),
                        Matchers.eq(imageFileName));
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
                .getFile(Matchers.eq(thumbsPhysicalPath));
    }

    @Test
    public void get_mustCreateThumbnailFile() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(randomFileCreator, times(1))
                .createFile(
                        Matchers.eq(thumbFileNamePrefix),
                        Matchers.eq("." + extension),
                        Matchers.eq(thumbsDirectory));
    }

    @Test
    public void get_mustResizeImageFile() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(imageFileResizer, times(1))
                .resize(
                        Matchers.eq(imageFile),
                        Matchers.eq(thumbFile),
                        Matchers.eq(extension),
                        Matchers.eq(thumbSize));
    }

    @Test
    public void get_mustSaveThumbnailToTheMetadatastore() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(thumbnailsSvc, times(1))
                .save(Matchers.any(Thumbnail.class));
    }

    @Test
    public void get_mustSaveThumbnailToTheMetadatastoreWithTheRightFilename() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(thumbnailsSvc, times(1))
                .save(Matchers.argThat(new ArgumentMatcher<Thumbnail>() {

                    @Override
                    public boolean matches(Object argument) {
                        return thumbFileName.equals(
                                ((Thumbnail)argument).fileName);
                    }
                }));
    }

    @Test
    public void get_mustSaveThumbnailToTheMetadatastoreWithTheRightOwnerId() throws Exception {
        controller.create(objectId, imageId);

        Mockito.verify(thumbnailsSvc, times(1))
                .save(Matchers.argThat(new ArgumentMatcher<Thumbnail>() {

                    @Override
                    public boolean matches(Object argument) {
                        return objectId == ((Thumbnail)argument).ownerId;
                    }
                }));
    }

    @Test
    public void get_mustSaveThumbnailToTheMetadatastoreWithTheOriginalId_ifThumbnailAlreadyExisted() throws Exception {
        setupToReturnOriginalThumbnail();

        controller.create(objectId, imageId);

        Mockito.verify(thumbnailsSvc, times(1))
                .save(Matchers.argThat(new ArgumentMatcher<Thumbnail>() {

                    @Override
                    public boolean matches(Object argument) {
                        return originalThumbId == ((Thumbnail)argument).id;
                    }
                }));
    }

    @Test
    public void get_inCaseOfExceptionResizingImage_mustDeleteThumbnailFile()
            throws Exception
    {
        setResizerToThrowException();

        testThumbFileDeletion();
    }

    @Test(expected = Exception.class)
    public void get_inCaseOfExceptionResizingImage_mustRethrowException()
            throws Exception
    {
        setResizerToThrowException();

        controller.create(objectId, imageId);
    }

    @Test
    public void get_inCaseOfExceptionSavingToTheMetadatastore_mustDeleteThumbnailFile()
            throws Exception
    {
        setServiceSaveThumbnailToThrowException();

        testThumbFileDeletion();
    }

    @Test(expected = Exception.class)
    public void get_inCaseOfExceptionSavingToTheMetadatastore_mustRethrowException()
            throws Exception
    {
        setServiceSaveThumbnailToThrowException();

        controller.create(objectId, imageId);
    }

    @Test
    public void get_ifObjectOriginallyHadAThumbnail_mustGetOriginalThumbFile() throws Exception {
        setupToReturnOriginalThumbnail();

        controller.create(objectId, imageId);

        Mockito.verify(fileFactory, times(1))
                .getFile(
                        Matchers.eq(thumbsPhysicalPath),
                        Matchers.eq(origThumbName));
    }

    @Test
    public void get_ifObjectOriginallyHadAThumbnail_mustDeleteOriginalThumbFile() throws Exception {
        setupToReturnOriginalThumbnail();

        controller.create(objectId, imageId);

        Mockito.verify(origThumbFile, times(1))
                .delete();
    }

    @Test
    public void get_inCaseOfExceptionSavingToTheMetadatastore_mustNotDeleteOriginalThumbFile() throws Exception {
        setServiceSaveThumbnailToThrowException();

        testOrigThumbNonDeletion();
    }

    @Test
    public void get_inCaseOfExceptionResizingImage_mustNotDeleteOriginalThumbFile() throws Exception {
        setResizerToThrowException();

        testOrigThumbNonDeletion();
    }

    @Test
    public void get_inCaseOfExceptionDeletingOriginalThumb_mustContinueExecution() throws Exception {
        setupToReturnOriginalThumbnail();

        Mockito.doThrow(Exception.class)
                .when(origThumbFile).delete();

        controller.create(objectId, imageId);
    }


    private void setResizerToThrowException() throws Exception {
        Mockito.doThrow(Exception.class)
                .when(imageFileResizer)
                .resize(
                        Matchers.any(File.class),
                        Matchers.any(File.class),
                        Matchers.anyString(),
                        Matchers.anyInt());
    }

    private void setServiceSaveThumbnailToThrowException() throws Exception {
        Mockito.doThrow(Exception.class)
                .when(thumbnailsSvc)
                .save(Matchers.any(Thumbnail.class));
    }

    private void testThumbFileDeletion() {
        try {
            controller.create(objectId, imageId);
        } catch(Exception e) {}

        Mockito.verify(thumbFile, times(1)).delete();
    }

    private void testOrigThumbNonDeletion() {
        try {
            controller.create(objectId, imageId);
        } catch(Exception e) {}


        Mockito.verifyNoMoreInteractions(origThumbFile);
    }

    private void setupToReturnOriginalThumbnail() {
        Mockito.when(thumbnailsSvc
                .getByOwner(
                        Matchers.eq(objectId)))
                .thenReturn(origThumbnail);
    }
}