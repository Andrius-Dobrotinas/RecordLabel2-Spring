package com.andrewd.recordlabel.web.io;

import com.andrewd.recordlabel.io.FileHelper;
import org.assertj.core.util.Files;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@RunWith(MockitoJUnitRunner.class)
public class MultipartFileSaveServiceTests {

    MultipartFileSaveService component;
    MultipartFile file;
    String fileName;
    File targetDirectory;

    File result;

    @Before
    public void before() throws IOException {
        component = new MultipartFileSaveService();

        targetDirectory = Files.newTemporaryFolder();

        fileName = "file name.ext";
        file = Mockito.mock(MultipartFile.class);
        Mockito.when(file.getOriginalFilename()).thenReturn(fileName);
    }

    @After
    public void cleanup() {
        result.delete();
        targetDirectory.delete();
    }

    @Test
    public void mustSaveFile() throws IOException {
        result = component.saveFile(file, targetDirectory);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.exists());
    }

    @Test
    public void mustSaveFileToTheSpecifiedDirectory() throws IOException {
        result = component.saveFile(file, targetDirectory);

        Assert.assertEquals(targetDirectory.getAbsolutePath(), result.getParent());
    }

    @Test
    public void mustSaveFileUsingARandomGeneratedName() throws IOException {
        result = component.saveFile(file, targetDirectory);
        File result2 = component.saveFile(file, targetDirectory);

        Assert.assertNotEquals(fileName, result.getName());
        Assert.assertNotEquals(result.getName(), result2.getName());

        result2.delete();
    }

    @Test
    public void mustSaveFileUsingTheOriginalFileExtension() throws IOException {
        result = component.saveFile(file, targetDirectory);

        String originalExtension = FileHelper.getFileExtension(fileName);
        String resultExtension = FileHelper.getFileExtension(fileName);

        Assert.assertEquals(originalExtension, resultExtension);
    }
}