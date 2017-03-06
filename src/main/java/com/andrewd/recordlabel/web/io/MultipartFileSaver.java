package com.andrewd.recordlabel.web.io;

import org.springframework.web.multipart.MultipartFile;
import java.io.*;

/**
 * Represents a function that saves multipart file on file system in
 * a specified directory
 */
public interface MultipartFileSaver {

    /**
     * Saves multipart file on file system in a specified directory
     * @param file file to save to the file system
     * @param directory directory where file is to be saved
     * @return
     * @throws IOException
     */
    File saveFile(MultipartFile file, File directory) throws IOException;
}