package com.andrewd.recordlabel.web.io;

import com.andrewd.recordlabel.WebConfig;
import com.andrewd.recordlabel.io.FileHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

/**
 * Saves multipart file on file system in a specified directory using a randomly
 * generated name
 */
@Component
public class MultipartFileSaverDefault implements MultipartFileSaver {

    @Value("${" + WebConfig.IMAGES_PREFIX + "}")
    public String fileNamePrefix;

    @Override
    public File saveFile(MultipartFile file, File directory) throws IOException {
        if (file == null)
            throw new IllegalArgumentException("file is null");
        if (directory == null)
            throw new IllegalArgumentException("directory is null");
        if (fileNamePrefix == null)
            throw new IllegalArgumentException("fileNamePrefix is null");
        if (fileNamePrefix.length() < 3)
            throw new IllegalArgumentException("fileNamePrefix must be at least 3 chars long");

        String extension = FileHelper.getFileExtension(file.getOriginalFilename());

        File destination = File.createTempFile(fileNamePrefix, extension, directory);
        file.transferTo(destination);

        return destination;
    }
}