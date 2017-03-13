package com.andrewd.recordlabel.io;

import java.io.File;

/**
 * Returns an instance of File for a given file name
 */
public interface FileFactory {
    File getFile(String filename);
    File getFile(String directory, String filename);
}