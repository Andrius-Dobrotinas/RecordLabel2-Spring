package com.andrewd.recordlabel.web.components;

import com.andrewd.recordlabel.web.io.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;
import java.util.function.Function;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class MultipartFileUploadServiceDefaultTests {

    @InjectMocks
    TransactionalMultipartFileUploaderDefault svc;

    @Mock
    MultipartFileSaver multipartFileSaver;

    private File targetDirectory;
    private MultipartFile fileToUpload1;
    private MultipartFile fileToUpload2;
    private MultipartFile[] filesToUpload;

    private File savedFile1;
    private File savedFile2;

    private Function<List<File>, List<String>> function;
    private List<String> functionResult;

    @Before
    public void before() throws IOException {
        targetDirectory = new File("");
        fileToUpload1 = Mockito.mock(MultipartFile.class);
        fileToUpload2 = Mockito.mock(MultipartFile.class);

        filesToUpload = new MultipartFile[] { fileToUpload1, fileToUpload2 };

        savedFile1 = Mockito.mock(File.class);
        savedFile2 = Mockito.mock(File.class);

        Mockito.when(multipartFileSaver
                .saveFile(Matchers.eq(fileToUpload1), Matchers.any()))
                .thenReturn(savedFile1);
        Mockito.when(multipartFileSaver
                .saveFile(Matchers.eq(fileToUpload2), Matchers.any()))
                .thenReturn(savedFile2);

        functionResult = new ArrayList<>();
        functionResult.add("some data");

        function = Mockito.mock(Function.class);
        Mockito.when(function.apply(Matchers.anyListOf(File.class)))
                .thenReturn(functionResult);
    }

    @Test
    public void uploadFiles_mustSaveEachFileToTheTargetDir() throws IOException, FileSaveException {
        svc.uploadFiles(filesToUpload, targetDirectory, function);

        Mockito.verify(multipartFileSaver, times(1))
                .saveFile(Matchers.eq(fileToUpload1), Matchers.eq(targetDirectory));
        Mockito.verify(multipartFileSaver, times(1))
                .saveFile(Matchers.eq(fileToUpload2), Matchers.eq(targetDirectory));
    }

    @Test(expected = FileSaveException.class)
    public void uploadFiles_onErrorSavingFiles_mustThrowException() throws FileSaveException, IOException {
        Mockito.when(multipartFileSaver
                .saveFile(Matchers.eq(fileToUpload1), Matchers.any()))
                .thenThrow(IOException.class);

        svc.uploadFiles(filesToUpload, targetDirectory, function);
    }

    /**
     * First file will be saved successfully and an exception will be
     * thrown saving the second file
     */
    @Test
    public void uploadFiles_onErrorSavingFiles_whenAtLeastOneFileHasBeenSaved_mustDeleteSavedFiles() throws IOException {
        Mockito.when(multipartFileSaver
                .saveFile(Matchers.eq(fileToUpload2), Matchers.any()))
                .thenThrow(IOException.class);

        try {
            svc.uploadFiles(filesToUpload, targetDirectory, function);
        }
        catch (FileSaveException e) { }

        Mockito.verify(savedFile1, times(1)).delete();
    }

    @Test
    public void uploadFiles_mustRunTheFunctionWithSavedFiles() throws FileSaveException {
        List<File> savedFiles = new ArrayList<>();
        savedFiles.add(savedFile1);
        savedFiles.add(savedFile2);

        svc.uploadFiles(filesToUpload, targetDirectory, function);

        Mockito.verify(function, times(1)).apply(Matchers.eq(savedFiles));
    }

    @Test
    public void uploadFiles_mustReturnWhateverTheFunctionReturns()
            throws FileSaveException {

        List<String> result = svc.uploadFiles(filesToUpload, targetDirectory, function);

        Assert.assertSame(functionResult, result);
    }

    @Test(expected = FileSaveException.class)
    public void uploadFiles_onErrorRunningFunction_mustThrowException()
            throws FileSaveException, IOException {

        Mockito.doThrow(Exception.class)
                .when(function).apply(Matchers.any());

        svc.uploadFiles(filesToUpload, targetDirectory, function);
    }

    @Test
    public void uploadFiles_onErrorRunningFunction_mustDeleteAllSavedFiles()
            throws IOException {

        Mockito.when(function.apply(Matchers.any()))
                .thenThrow(Exception.class);

        try {
            svc.uploadFiles(filesToUpload, targetDirectory, function);
        }
        catch (FileSaveException e) { }

        Mockito.verify(savedFile1, times(1)).delete();
        Mockito.verify(savedFile2, times(1)).delete();
    }
}