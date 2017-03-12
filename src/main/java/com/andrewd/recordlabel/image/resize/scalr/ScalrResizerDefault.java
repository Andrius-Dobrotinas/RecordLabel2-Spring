package com.andrewd.recordlabel.image.resize.scalr;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;
import java.awt.image.BufferedImage;
import java.io.*;

@Component
public class ScalrResizerDefault implements ScalrResizer {

    @Override
    public BufferedImage resize(BufferedImage image,
                                String imageFormatName,
                                int targetSize,
                                Scalr.Method scalingMethod,
                                Scalr.Mode resizeMode)
            throws IOException
    {
        return Scalr
                .resize(image, scalingMethod, resizeMode, targetSize);
    }
}