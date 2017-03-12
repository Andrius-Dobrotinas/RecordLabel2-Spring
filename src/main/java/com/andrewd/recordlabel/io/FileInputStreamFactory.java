package com.andrewd.recordlabel.io;

import org.springframework.stereotype.Component;
import java.io.*;

/**
 * Returns an input stream for a given file
 */
@Component
public class FileInputStreamFactory implements IOFunction<File, FileInputStream> {

    @Override
    public FileInputStream apply(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }
}