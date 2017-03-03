package com.andrewd.recordlabel.web.io;

import com.andrewd.recordlabel.io.FileHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@Component
public class MultipartFileSaverDefault implements MultipartFileSaver {

    @Override
    public File saveFile(MultipartFile file, File directory) throws IOException {
        String extension = FileHelper.getFileExtension(file.getOriginalFilename());

        // TODO: prefix must be supplied as an argument
        File destination = File.createTempFile("img", extension, directory);
        file.transferTo(destination);

        return destination;
    }
}