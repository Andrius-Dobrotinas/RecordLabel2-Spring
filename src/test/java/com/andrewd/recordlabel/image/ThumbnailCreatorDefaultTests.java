package com.andrewd.recordlabel.image;

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
    ThumbnailCreatorDefault component;

    @Mock
    ReleaseService releasesSvc;

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
    private final int ownerId = 711;
    private final int sourceImageId = 2;
    private final String imageFileName = "image.jpg";
    private final String fullImagePath = "img/image.jpg";
    private final String extension = "jpg";

    private final String origThumbName = "original thumbnail.png";
    private final int originalThumbId = 7;

    private Image sourceImage;
    private File imageFile;
    private File thumbFile;
    private File thumbsDirectory;
    private Thumbnail origThumbnail;
    private File origThumbFile;

    @Before
    public void before() throws Exception {
        sourceImage = new Image(imageFileName);
        sourceImage.id = sourceImageId;
        sourceImage.ownerId = ownerId;
        imageFile = new File(fullImagePath);
        thumbFile = Mockito.mock(File.class);
        thumbsDirectory = new File(thumbsPhysicalPath);
        origThumbnail = Mockito.mock(Thumbnail.class);
        origThumbnail.fileName = origThumbName;
        origThumbnail.id = originalThumbId;
        origThumbFile = Mockito.mock(File.class);

        component.imagesPhysicalPath = imagesPhysicalPath;
        component.thumbsPhysicalPath = thumbsPhysicalPath;
        component.thumbFileNamePrefix = thumbFileNamePrefix;
        component.thumbSize = thumbSize;

        Mockito.when(thumbFile
                .getName())
                .thenReturn(thumbFileName);

        Mockito.when(releasesSvc
                .objectExists(
                        Matchers.eq(ownerId)))
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
    public void mustCheckIfTargetObjectExists() throws Exception {
        component.createThumbnail(sourceImage);

        Mockito.verify(releasesSvc, times(1))
                .objectExists(
                        Matchers.eq(ownerId));
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void whenTargetObjectDoesNotExist_mustThrowException() throws Exception {
        sourceImage.ownerId = 1; // id of a supposedly non-existent object

        component.createThumbnail(sourceImage);
    }

    @Test
    public void mustRetrieveCurrentThumbnailFromTheDatastore() throws Exception {
        component.createThumbnail(sourceImage);

        Mockito.verify(thumbnailsSvc, times(1))
                .getByOwner(
                        Matchers.eq(ownerId));
    }

    @Test
    public void mustReadImageFile() throws Exception {
        component.createThumbnail(sourceImage);

        Mockito.verify(fileFactory, times(1))
                .getFile(
                        Matchers.eq(imagesPhysicalPath),
                        Matchers.eq(imageFileName));
    }

    @Test
    public void mustGetFileExtension() throws Exception {
        component.createThumbnail(sourceImage);

        Mockito.verify(fileExtensionGetter, times(1))
                .getFileExtension(
                        Matchers.eq(imageFileName),
                        Matchers.eq(false));
    }

    @Test
    public void mustGetPhysicalThumbsDirectory() throws Exception {
        component.createThumbnail(sourceImage);

        Mockito.verify(fileFactory, times(1))
                .getFile(Matchers.eq(thumbsPhysicalPath));
    }

    @Test
    public void mustCreateThumbnailFile() throws Exception {
        component.createThumbnail(sourceImage);

        Mockito.verify(randomFileCreator, times(1))
                .createFile(
                        Matchers.eq(thumbFileNamePrefix),
                        Matchers.eq("." + extension),
                        Matchers.eq(thumbsDirectory));
    }

    @Test
    public void mustResizeImageFile() throws Exception {
        component.createThumbnail(sourceImage);

        Mockito.verify(imageFileResizer, times(1))
                .resize(
                        Matchers.eq(imageFile),
                        Matchers.eq(thumbFile),
                        Matchers.eq(extension),
                        Matchers.eq(thumbSize));
    }

    @Test
    public void mustSaveThumbnailToTheMetadatastore() throws Exception {
        component.createThumbnail(sourceImage);

        Mockito.verify(thumbnailsSvc, times(1))
                .save(Matchers.any(Thumbnail.class));
    }

    @Test
    public void mustSaveThumbnailToTheMetadatastoreWithTheRightFilename() throws Exception {
        component.createThumbnail(sourceImage);

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
    public void mustSaveThumbnailToTheMetadatastoreWithTheRightOwnerId() throws Exception {
        component.createThumbnail(sourceImage);

        Mockito.verify(thumbnailsSvc, times(1))
                .save(Matchers.argThat(new ArgumentMatcher<Thumbnail>() {

                    @Override
                    public boolean matches(Object argument) {
                        return ownerId == ((Thumbnail)argument).ownerId;
                    }
                }));
    }

    @Test
    public void mustSaveThumbnailToTheMetadatastoreWithTheOriginalId_ifThumbnailAlreadyExisted() throws Exception {
        setupToReturnOriginalThumbnail();

        component.createThumbnail(sourceImage);

        Mockito.verify(thumbnailsSvc, times(1))
                .save(Matchers.argThat(new ArgumentMatcher<Thumbnail>() {

                    @Override
                    public boolean matches(Object argument) {
                        return originalThumbId == ((Thumbnail)argument).id;
                    }
                }));
    }

    @Test
    public void inCaseOfExceptionResizingImage_mustDeleteThumbnailFile()
            throws Exception
    {
        setResizerToThrowException();

        testThumbFileDeletion();
    }

    @Test(expected = Exception.class)
    public void inCaseOfExceptionResizingImage_mustRethrowException()
            throws Exception
    {
        setResizerToThrowException();

        component.createThumbnail(sourceImage);
    }

    @Test
    public void inCaseOfExceptionSavingToTheMetadatastore_mustDeleteThumbnailFile()
            throws Exception
    {
        setServiceSaveThumbnailToThrowException();

        testThumbFileDeletion();
    }

    @Test(expected = Exception.class)
    public void inCaseOfExceptionSavingToTheMetadatastore_mustRethrowException()
            throws Exception
    {
        setServiceSaveThumbnailToThrowException();

        component.createThumbnail(sourceImage);
    }

    @Test
    public void ifObjectOriginallyHadAThumbnail_mustGetOriginalThumbFile() throws Exception {
        setupToReturnOriginalThumbnail();

        component.createThumbnail(sourceImage);

        Mockito.verify(fileFactory, times(1))
                .getFile(
                        Matchers.eq(thumbsPhysicalPath),
                        Matchers.eq(origThumbName));
    }

    @Test
    public void ifObjectOriginallyHadAThumbnail_mustDeleteOriginalThumbFile() throws Exception {
        setupToReturnOriginalThumbnail();

        component.createThumbnail(sourceImage);

        Mockito.verify(origThumbFile, times(1))
                .delete();
    }

    @Test
    public void inCaseOfExceptionSavingToTheMetadatastore_mustNotDeleteOriginalThumbFile() throws Exception {
        setServiceSaveThumbnailToThrowException();

        testOrigThumbNonDeletion();
    }

    @Test
    public void inCaseOfExceptionResizingImage_mustNotDeleteOriginalThumbFile() throws Exception {
        setResizerToThrowException();

        testOrigThumbNonDeletion();
    }

    @Test
    public void inCaseOfExceptionDeletingOriginalThumb_mustContinueExecution() throws Exception {
        setupToReturnOriginalThumbnail();

        Mockito.doThrow(Exception.class)
                .when(origThumbFile).delete();

        component.createThumbnail(sourceImage);
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
            component.createThumbnail(sourceImage);
        } catch(Exception e) {}

        Mockito.verify(thumbFile, times(1)).delete();
    }

    private void testOrigThumbNonDeletion() {
        try {
            component.createThumbnail(sourceImage);
        } catch(Exception e) {}


        Mockito.verifyNoMoreInteractions(origThumbFile);
    }

    private void setupToReturnOriginalThumbnail() {
        Mockito.when(thumbnailsSvc
                .getByOwner(
                        Matchers.eq(ownerId)))
                .thenReturn(origThumbnail);
    }
}