package com.andrewd.recordlabel.io;

import java.io.*;

/**
 * Creates a file with random name
 */
@FunctionalInterface
public interface RandomFileCreator {

    /**
     * Physically creates a file with random name that's unique for the
     * target directory using the supplied file name prefix and suffix
     * @param prefix random file name prefix
     * @param suffix random file name suffix
     * @param directory directory where file is to be created
     * @return an instance of created file
     * @throws IOException
     */
    File createFile(String prefix, String suffix, File directory)
            throws IOException;
}