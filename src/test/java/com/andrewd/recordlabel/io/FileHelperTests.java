package com.andrewd.recordlabel.io;

import com.andrewd.recordlabel.io.FileHelper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class FileHelperTests {

    @Test
    public void getFileExtension_mustReturnSubstringStartingWithLastDot() throws IOException {
        String ext = ".txt";
        String fileName = "seven.txt";

        String result = FileHelper.getFileExtension(fileName);

        Assert.assertEquals(ext, result);
    }

    @Test
    public void getFileExtension_mustReturnSubstringStartingWithLastDot_forLongerExtensionsToo() throws IOException {
        String ext = ".textfile";
        String fileName = "seven.textfile";

        String result = FileHelper.getFileExtension(fileName);

        Assert.assertEquals(ext, result);
    }

    @Test
    public void getFileExtension_mustReturnSubstringStartingWithLastDot_forShorterExtensionsToo() throws IOException {
        String ext = ".t";
        String fileName = "seven.t";

        String result = FileHelper.getFileExtension(fileName);

        Assert.assertEquals(ext, result);
    }

    @Test
    public void getFileExtension_mustReturnEmptyStringIfNoDotFoundWithinString() throws IOException {
        String fileName = "seven";

        String result = FileHelper.getFileExtension(fileName);

        Assert.assertEquals("", result);
    }
}