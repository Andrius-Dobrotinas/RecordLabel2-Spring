package com.andrewd.recordlabel.io;

import org.springframework.stereotype.Component;
import java.io.*;

/**
 * Returns an output stream for a given file
 */
@Component
public class FileOutputStreamFactory implements IOFunction<File, FileOutputStream> {

    @Override
    public FileOutputStream apply(File file) throws FileNotFoundException {
        return new FileOutputStream(file);
    }
}