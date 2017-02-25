package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.data.model.*;
import com.andrewd.recordlabel.data.service.ReleaseService;
import com.andrewd.recordlabel.web.io.FileSaveException;
import com.andrewd.recordlabel.web.service.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ImagesUploadControllerTests {

    @InjectMocks
    ImagesUploadController controller;

    @Mock
    MultipartFileUploadService fileUploader;

    @Mock
    ReleaseService releaseSvc;

    @Mock
    Environment env;

    int objectId;
    MultipartFile file1;
    MultipartFile[] files;
    String file1Name;
    String[] fileNames;
    ContentBase owner;

    final String imgVirtualPathName = "/img/";

    @Before
    public void before() throws IOException, FileSaveException {
        objectId = 1;
        file1 = Mockito.mock(MultipartFile.class);

        files = new MultipartFile[] { file1 };

        file1Name = "seven.eleven";
        fileNames = new String[] { file1Name };

        owner = new Release();

        Mockito.when(releaseSvc.getObject(Matchers.any(), Matchers.anyInt())).thenReturn(owner);

        Mockito.when(fileUploader
                .uploadFiles(Matchers.any(ContentBase.class), Matchers.any(MultipartFile[].class), Matchers.any(File.class)))
                .thenReturn(fileNames);

        Mockito.when(env.getProperty(Matchers.eq("recordlabel.img.virtualpath")))
                .thenReturn(imgVirtualPathName);
        Mockito.when(env.getProperty(Matchers.eq("recordlabel.img.path")))
                .thenReturn("");
    }

    @Test
    public void uploadFiles_mustRetrieveOwnerObjectFromTheDbById() throws FileSaveException {
        controller.upload(objectId, files);

        Mockito.verify(releaseSvc, times(1)).getObject(Matchers.eq(ContentBase.class), Matchers.eq(objectId));
    }

    @Test
    public void uploadImage_uploadAllFiles() throws Exception {
        controller.upload(objectId, files);

        Mockito.verify(fileUploader, times(1))
                .uploadFiles(Matchers.eq(owner), Matchers.eq(files), Matchers.any(File.class));
    }

    @Test
    public void uploadImage_ifObjectDoesNotExist_mustReturnBadRequest() throws Exception {
        Mockito.when(releaseSvc.getObject(Matchers.any(), Matchers.anyInt())).thenReturn(null);

        ResponseEntity response = controller.upload(objectId, files);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void uploadImage_ifObjectDoesNotExist_responseBodyMustBeErrorResponse() throws Exception {
        Mockito.when(releaseSvc.getObject(Matchers.any(), Matchers.anyInt())).thenReturn(null);

        ResponseEntity response = controller.upload(objectId, files);

        Assert.assertNotNull(response.getBody().getClass());
        Assert.assertEquals(ErrorResponse.class, response.getBody().getClass());
    }

    @Test
    public void uploadImage_responseBodyMustContainAnArrayOfStringInCaseOfSuccess() throws Exception {
        ResponseEntity response = controller.upload(objectId, files);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(String[].class, response.getBody().getClass());
    }

    @Test
    public void uploadImage_responseBodyMustContainAnArrayOfFileNamesPrefixedWithDirectoryMapName() throws Exception {
        String[] expectedFileNames = new String[] { imgVirtualPathName + file1Name };

        ResponseEntity response = controller.upload(objectId, files);

        Assert.assertArrayEquals(expectedFileNames, (String[])response.getBody());
    }
}