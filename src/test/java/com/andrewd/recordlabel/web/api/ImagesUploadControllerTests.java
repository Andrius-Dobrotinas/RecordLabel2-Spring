package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.data.services.ImagesService;
import com.andrewd.recordlabel.data.services.ReleaseService;
import com.andrewd.recordlabel.supermodels.Image;
import com.andrewd.recordlabel.web.io.FileSaveException;
import com.andrewd.recordlabel.web.models.ErrorResponse;
import com.andrewd.recordlabel.web.components.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;
import java.util.function.Function;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ImagesUploadControllerTests {

    @InjectMocks
    ImagesUploadController controller;

    @Mock
    TransactionalMultipartFileUploader fileUploader;

    @Mock
    ReleaseService releaseSvc;

    @Mock
    private ImagesService imagesSvc;

    @Mock
    ImageFilenameUrlifier imgUrlifier;

    @Captor
    ArgumentCaptor<Function<List<File>, List<Image>>> funcCaptor;

    private MultipartFile multipartFile1;
    private MultipartFile[] files;
    private List<Image> savedImages;
    private Image savedImage1;
    private Image savedImage2;
    private List<File> savedFiles;
    private File file1;
    private File file2;

    private final int objectId = 1;
    private final String imgVirtualPath = "/img/";
    private final String imgPhysicalPath = "";

    @Before
    public void before() throws IOException, FileSaveException {
        multipartFile1 = Mockito.mock(MultipartFile.class);
        files = new MultipartFile[] { multipartFile1 };

        controller.imagesVirtualPath = imgVirtualPath;
        controller.imagesPhysicalPath = imgPhysicalPath;

        savedImages = new ArrayList<>();
        savedImage1 = new Image("one");
        savedImage2 = new Image("two");
        savedImages.add(savedImage1);
        savedImages.add(savedImage2);

        savedFiles = new ArrayList<>();
        file1 = new File("sample.img");
        file2 = new File("sample 2.img");
        savedFiles.add(file1);
        savedFiles.add(file2);

        Mockito.when(releaseSvc.objectExists(Matchers.anyInt())).thenReturn(true);

        Mockito.when(imagesSvc.save(Matchers.anyInt(), Matchers.anyListOf(Image.class)))
                .thenReturn(savedImages);

        Mockito.when(fileUploader.uploadFiles(Matchers.any(MultipartFile[].class),
                Matchers.any(File.class), Matchers.any(Function.class)))
                .thenAnswer(x -> savedImages);
    }

    @Test
    public void uploadFiles_mustCheckIfObjectExistsInTheDb() throws FileSaveException {
        controller.upload(objectId, files);

        Mockito.verify(releaseSvc, times(1))
                .objectExists(Matchers.eq(objectId));
    }

    @Test
    public void uploadImage_ifObjectDoesNotExist_mustReturnBadRequest() throws Exception {
        Mockito.when(releaseSvc.objectExists(Matchers.anyInt())).thenReturn(false);

        ResponseEntity response = controller.upload(objectId, files);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void uploadImage_ifObjectDoesNotExist_responseBodyMustBeErrorResponse() throws Exception {
        Mockito.when(releaseSvc.objectExists(Matchers.anyInt())).thenReturn(false);

        ResponseEntity response = controller.upload(objectId, files);

        Assert.assertNotNull(response.getBody().getClass());
        Assert.assertEquals(ErrorResponse.class, response.getBody().getClass());
    }

    @Test
    public void uploadImage_mustUploadAllFiles() throws Exception {
        controller.upload(objectId, files);

        Mockito.verify(fileUploader, times(1))
                .uploadFiles(Matchers.eq(files), Matchers.any(File.class),
                        Matchers.any());
    }

    @Test
    public void uploadImage_mustPassFunctionToUploadAllFiles() throws Exception {
        controller.upload(objectId, files);

        Mockito.verify(fileUploader, times(1))
                .uploadFiles(Matchers.eq(files), Matchers.any(File.class),
                        funcCaptor.capture());

        Function<List<File>, List<Image>> function = null;
        try {
            function = funcCaptor.getValue();
        } catch(Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull("Must use Function<List<File>, List<Image>> type function", function);
    }

    // Runs a test with standard arguments and returns a function that was passed to fileUploader.uploadFiles
    private Function<List<File>, List<Image>> uploadImage_runAndCaptureTheFunction()
            throws FileSaveException {
        controller.upload(objectId, files);

        Mockito.verify(fileUploader)
                .uploadFiles(Matchers.eq(files), Matchers.any(File.class),
                        funcCaptor.capture());

        return funcCaptor.getValue();
    }

    @Test
    public void uploadImage_functionPassedToUploadAllFiles_mustInvokeImagesRepositorySaveWithAListOfImages() throws Exception {
        Function<List<File>, List<Image>> function = uploadImage_runAndCaptureTheFunction();

        function.apply(savedFiles);

        Mockito.verify(imagesSvc, times(1))
                .save(Matchers.eq(objectId), Matchers.anyListOf(Image.class));
    }

    @Test
    public void uploadImage_functionPassedToUploadAllFiles_mustReturnAListOfImagesReturnedFromImagesServiceSave() throws Exception {
        Function<List<File>, List<Image>> function = uploadImage_runAndCaptureTheFunction();

        List<Image> result = function.apply(savedFiles);

        Assert.assertSame("Function must return the same list that is returned by ImagesService's Save",
                savedImages, result);
    }

    @Test
    public void uploadImage_functionPassedToUploadAllFiles_eachImagePassedToImagesServiceSaveMustHaveHaveFileNameCopiedFromCorrespondingFile() throws Exception {
        Function<List<File>, List<Image>> function = uploadImage_runAndCaptureTheFunction();

        function.apply(savedFiles);

        ArgumentCaptor<List<Image>> imgCaptor = new ArgumentCaptor<>();
        Mockito.verify(imagesSvc, times(1)).save(Matchers.eq(objectId), imgCaptor.capture());

        List<Image> imagesToSave = imgCaptor.getValue();

        Assert.assertEquals(file1.getName(), imagesToSave.get(0).fileName);
        Assert.assertEquals(file2.getName(), imagesToSave.get(1).fileName);
    }

    @Test
    public void get_mustUseUrlifierToChangeEachImagesFileNameToAUrl() throws FileSaveException {
        controller.upload(objectId, files);

        Mockito.verify(imgUrlifier, times(1)).urlify(Matchers.eq(savedImage1), Matchers.eq(imgVirtualPath));
        Mockito.verify(imgUrlifier, times(1)).urlify(Matchers.eq(savedImage1), Matchers.eq(imgVirtualPath));
    }

    @Test
    public void uploadImage_responseBodyMustContainAListOfImageObjectsRepresentingUploadedFiles()
            throws Exception {
        ResponseEntity response = controller.upload(objectId, files);

        List<Image> result = (List<Image>)response.getBody();

        Assert.assertArrayEquals(savedImages.toArray(), result.toArray());
    }
}