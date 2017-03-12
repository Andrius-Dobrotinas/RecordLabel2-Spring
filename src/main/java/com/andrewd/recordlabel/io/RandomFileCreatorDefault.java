package com.andrewd.recordlabel.io;

import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Creates a file with random name using java.io.File.createTempFile
 */
@Component
public class RandomFileCreatorDefault implements RandomFileCreator {

    /**
     * Physically creates a file with random name that's unique for the
     * target directory using the supplied file name prefix and suffix
     * @param prefix random file name prefix. Must be at least
     *               3 characters long
     * @param suffix random file name suffix.
     * @param directory directory where file is to be created
     * @return an instance of created file
     * @throws IOException
     */
    @Override
    public File createFile(String prefix, String suffix, File directory)
            throws IOException
    {
        return File.createTempFile(prefix, suffix, directory);
    }
}