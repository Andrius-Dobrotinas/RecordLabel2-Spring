package com.andrewd.recordlabel.io;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.function.Function;

/**
 * Returns an instance of File for a given file name
 */
@Component
public class FileFactory implements Function<String, File> {

    @Override
    public File apply(String filename) {
        return new File(filename);
    }
}