package com.andrewd.recordlabel.web.io;

import com.andrewd.recordlabel.io.RandomFileCreatorDefault;
import org.assertj.core.util.Files;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import java.io.*;

@RunWith(MockitoJUnitRunner.class)
public class RandomFileCreatorDefaultTests {

    @InjectMocks
    RandomFileCreatorDefault component;

    private final String prefix = "img";
    private final String suffix = "tmp";
    private File targetDirectory;
    private File result;
    private File result2;
    private File result3;

    @Before
    public void before() throws IOException {
        targetDirectory = Files.newTemporaryFolder();
    }

    @After
    public void cleanup() {
        result.delete();

        if (result2 != null)
            result2.delete();
        if (result3 != null)
            result3.delete();

        targetDirectory.delete();
    }

    @Test
    public void mustGenerateAUniqueFileName() throws IOException {
        result = component.createFile(prefix, suffix, targetDirectory);
        result2 = component.createFile(prefix, suffix, targetDirectory);
        result3 = component.createFile(prefix, suffix, targetDirectory);

        Assert.assertNotEquals(
                "Each generated file name must be unique for that directory",
                result.getName(), result2.getName());
        Assert.assertNotEquals(
                "Each generated file name must be unique for that directory",
                result.getName(), result3.getName());
    }

    @Test
    public void mustCreateFileInTheTargetDirectory() throws IOException {
        result = component.createFile(prefix, suffix, targetDirectory);

        Assert.assertEquals(
                "File must be created in the target directory",
                targetDirectory.getAbsolutePath(), result.getParent());
    }

    @Test
    public void mustPhysicallyCreateAFile() throws IOException {
        result = component.createFile(prefix, suffix, targetDirectory);

        Assert.assertTrue("File must be physically created", result.exists());
    }

    @Test
    public void mustSaveFileUsingTheProvidedPrefix() throws IOException {
        result = component.createFile(prefix, suffix, targetDirectory);

        Assert.assertTrue(
                "File name must start with the supplied prefix",
                result.getName().startsWith(prefix));
    }

    @Test
    public void mustSaveFileUsingTheSuppliedSuffix() throws IOException {
        result = component.createFile(prefix, suffix, targetDirectory);

        Assert.assertTrue("File name must end with the supplied suffix",
                result.getName().endsWith(suffix));
    }
}