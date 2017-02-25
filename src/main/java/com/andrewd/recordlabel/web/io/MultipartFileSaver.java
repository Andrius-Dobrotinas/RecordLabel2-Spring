package com.andrewd.recordlabel.web.io;

import org.springframework.web.multipart.MultipartFile;
import java.io.*;

public interface MultipartFileSaver {
    File saveFile(MultipartFile files, File directory) throws IOException;
}