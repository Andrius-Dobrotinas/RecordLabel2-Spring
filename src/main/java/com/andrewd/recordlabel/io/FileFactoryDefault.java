package com.andrewd.recordlabel.io;

import org.springframework.stereotype.Component;
import java.io.File;

/**
 * Returns an instance of File for a given file name
 */
@Component
public class FileFactoryDefault implements FileFactory {

    @Override
    public File getFile(String filename) {
        return new File(filename);
    }

    @Override
    public File getFile(String directory, String filename) {
        return new File(directory, filename);
    }
}