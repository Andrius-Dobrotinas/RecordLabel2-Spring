package com.andrewd.recordlabel.io;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class FileExtensionGetterDefaultTests {

    @InjectMocks
    FileExtensionGetterDefault getter;

    @Test
    public void mustReturnSubstringStartingWithLastDot() throws IOException {
        String ext = "txt";
        String fileName = "seven.txt";

        String result = getter.getFileExtension(fileName);

        Assert.assertEquals(ext, result);
    }

    @Test
    public void mustReturnSubstringStartingWithLastDot_forLongerExtensionsToo() throws IOException {
        String ext = "eleven";
        String fileName = "seven.eleven";

        String result = getter.getFileExtension(fileName);

        Assert.assertEquals(ext, result);
    }

    @Test
    public void mustReturnSubstringStartingWithLastDot_forShorterExtensionsToo() throws IOException {
        String ext = "t";
        String fileName = "seven.t";

        String result = getter.getFileExtension(fileName);

        Assert.assertEquals(ext, result);
    }

    @Test
    public void mustReturnEmptyStringIfNoDotFoundWithinString() throws IOException {
        String fileName = "seven";

        String result = getter.getFileExtension(fileName);

        Assert.assertEquals("", result);
    }

    @Test
    public void whenIncludeDot_mustReturnSubstringStartingWithLastDot() throws IOException {
        String ext = ".txt";
        String fileName = "seven.txt";

        String result = getter.getFileExtension(fileName, true);

        Assert.assertEquals(ext, result);
    }

    @Test
    public void whenIncludeDot_mustReturnSubstringStartingWithLastDot_forLongerExtensionsToo() throws IOException {
        String ext = ".eleven";
        String fileName = "seven.eleven";

        String result = getter.getFileExtension(fileName, true);

        Assert.assertEquals(ext, result);
    }

    @Test
    public void whenIncludeDot_mustReturnSubstringStartingWithLastDot_forShorterExtensionsToo() throws IOException {
        String ext = ".t";
        String fileName = "seven.t";

        String result = getter.getFileExtension(fileName, true);

        Assert.assertEquals(ext, result);
    }
}