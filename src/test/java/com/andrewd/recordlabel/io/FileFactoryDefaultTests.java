package com.andrewd.recordlabel.io;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import java.io.File;

@RunWith(MockitoJUnitRunner.class)
public class FileFactoryDefaultTests {

    @InjectMocks
    FileFactoryDefault factory;

    private final String directory = "images\\";
    private final String filename = "new file.img";

    @Test
    public void mustReturnAnInstanceOfFile() {
        File file = factory.getFile(filename);

        Assert.assertNotNull(file);
    }

    @Test
    public void mustReturnAnInstanceOfFileWithTheSuppliedPath() {
        File file = factory.getFile(filename);

        Assert.assertEquals(filename, file.getPath());
    }

    @Test
    public void secondOverload_mustReturnAnInstanceOfFile() {
        File file = factory.getFile(directory, filename);

        Assert.assertNotNull(file);
    }

    @Test
    public void secondOverload_mustReturnAnInstanceOfFileWithTheSuppliedPath() {
        File file = factory.getFile(directory, filename);

        String expectedPath = directory + filename;

        Assert.assertEquals(expectedPath, file.getPath());
    }
}