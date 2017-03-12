package com.andrewd.recordlabel.image;

import com.andrewd.recordlabel.io.IOFunction;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Reads the supplied input stream and returns it as a buffered image.
 * A wrapper class for javax.imageio.ImageIO.read
 */
@Component
public class BufferedImageFactory implements IOFunction<InputStream, BufferedImage> {

    @Override
    public BufferedImage apply(InputStream input) throws IOException {
        return ImageIO.read(input);
    }
}