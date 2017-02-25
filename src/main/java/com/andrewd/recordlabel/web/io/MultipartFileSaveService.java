package com.andrewd.recordlabel.web.io;

import com.andrewd.recordlabel.io.FileHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@Component
public class MultipartFileSaveService implements MultipartFileSaver {

    public File saveFile(MultipartFile file, File directory) throws IOException {
        String extension = FileHelper.getFileExtension(file.getOriginalFilename());

        File destination = File.createTempFile("img", extension, directory);
        file.transferTo(destination);

        return destination;
    }
}