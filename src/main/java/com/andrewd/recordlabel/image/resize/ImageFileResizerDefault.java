package com.andrewd.recordlabel.image.resize;

import com.andrewd.recordlabel.io.IOFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.*;

@Component
public class ImageFileResizerDefault implements ImageFileResizer {

    @Autowired
    private IOFunction<File, FileInputStream> fileInputStreamFactory;

    @Autowired
    private IOFunction<File, FileOutputStream> fileOutputStreamFactory;

    @Autowired
    private ImageResizer imageResizer;

    public void resize(File source,
                       File destination,
                       String imageFormatName,
                       int targetSize)
            throws Exception
    {
        try (FileInputStream imageStream
                     = fileInputStreamFactory.apply(source);
             FileOutputStream thumbnailStream
                     = fileOutputStreamFactory.apply(destination)) {

            imageResizer.resizeImage(imageStream, thumbnailStream,
                    imageFormatName, targetSize);
        }
    }
}