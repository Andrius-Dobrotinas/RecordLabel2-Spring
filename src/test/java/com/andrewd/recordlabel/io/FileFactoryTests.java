package com.andrewd.recordlabel.io;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

@RunWith(MockitoJUnitRunner.class)
public class FileFactoryTests {

    @InjectMocks
    FileFactory factory;

    private final String filename = "new file.img";

    @Test
    public void mustReturnAnInstanceOfFile() {
        File file = factory.apply(filename);

        Assert.assertNotNull(file);
    }

    @Test
    public void mustReturnAnInstanceOfFileWithTheSuppliedPath() {
        File file = factory.apply(filename);

        Assert.assertEquals(filename, file.getPath());
    }
}