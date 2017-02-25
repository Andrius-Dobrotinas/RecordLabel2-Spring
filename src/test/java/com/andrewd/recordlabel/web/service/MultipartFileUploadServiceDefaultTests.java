package com.andrewd.recordlabel.web.service;

import com.andrewd.recordlabel.data.model.*;
import com.andrewd.recordlabel.data.service.*;
import com.andrewd.recordlabel.web.io.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class MultipartFileUploadServiceDefaultTests {

    @InjectMocks
    MultipartFileUploadServiceDefault svc;

    @Mock
    MultipartFileSaver multipartFileSaver;

    @Mock
    FileSaveToRepositoryService fileSaveToRepositoryService;

    ContentBase owner;

    File targetDirectory;
    MultipartFile file1;
    String file1Name = "file1Name";

    MultipartFile[] files;
    String[] fileNames;

    File savedFile1;

    @Before
    public void before() throws IOException {
        owner = new Release();

        targetDirectory = new File("");
        file1 = Mockito.mock(MultipartFile.class);

        files = new MultipartFile[] { file1 };
        fileNames = new String[] { file1Name };

        savedFile1 = Mockito.mock(File.class);

        Mockito.when(savedFile1.getName()).thenReturn(file1Name);

        Mockito.when(multipartFileSaver.saveFile(Matchers.any(), Matchers.any())).thenReturn(savedFile1);
    }

    @Test
    public void uploadFiles_mustSaveEachFileToTheTargetDir() throws IOException, FileSaveException {
        svc.uploadFiles(owner, files, targetDirectory);

        Mockito.verify(multipartFileSaver, times(1)).saveFile(Matchers.eq(file1), Matchers.any(File.class));
    }

    @Test
    public void uploadFiles_mustSaveEachEntryToTheDatabase() throws FileSaveException {
        svc.uploadFiles(owner, files, targetDirectory);

        Mockito.verify(fileSaveToRepositoryService, times(1)).save(Matchers.eq(owner), Matchers.eq(fileNames));
    }

    @Test
    public void uploadFiles_mustReturnFileNames() throws FileSaveException {
        String[] result = svc.uploadFiles(owner, files, targetDirectory);

        Assert.assertArrayEquals(fileNames, result);
    }

    @Test(expected = FileSaveException.class)
    public void uploadFiles_onErrorSavingFiles_mustThrowException() throws FileSaveException, IOException {

        Mockito.when(multipartFileSaver.saveFile(Matchers.eq(file1), Matchers.any()))
                .thenThrow(IOException.class);

        svc.uploadFiles(owner, files, targetDirectory);
    }

    @Test
    public void uploadFiles_onErrorSavingFiles_mustDeleteSavedFiles_whenAtLeastOneFileHasBeenSaved() throws IOException {

        MultipartFile file2 = Mockito.mock(MultipartFile.class);
        files = new MultipartFile[] { file1, file2 };

        Mockito.when(multipartFileSaver.saveFile(Matchers.eq(file2), Matchers.any()))
                .thenThrow(IOException.class);

        try {
            svc.uploadFiles(owner, files, targetDirectory);
        }
        catch (FileSaveException e) { }

        Mockito.verify(savedFile1, times(1)).delete();
    }

    @Test(expected = FileSaveException.class)
    public void uploadFiles_onErrorSavingToTheDb_mustThrowException() throws FileSaveException {
        Mockito.doThrow(IOException.class)
                .when(fileSaveToRepositoryService).save(Matchers.any(), Matchers.any());

        svc.uploadFiles(owner, files, targetDirectory);
    }

    @Test
    public void uploadFiles_onErrorSavingToTheDb_deletedSavedFiles() {
        Mockito.doThrow(IOException.class)
                .when(fileSaveToRepositoryService).save(Matchers.any(), Matchers.any());

        try {
            svc.uploadFiles(owner, files, targetDirectory);
        }
        catch (FileSaveException e) { }

        Mockito.verify(savedFile1, times(1)).delete();
    }
}