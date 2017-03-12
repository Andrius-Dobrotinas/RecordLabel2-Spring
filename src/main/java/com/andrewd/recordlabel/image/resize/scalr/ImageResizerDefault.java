package com.andrewd.recordlabel.image.resize.scalr;

import com.andrewd.recordlabel.WebConfig;
import com.andrewd.recordlabel.image.*;
import com.andrewd.recordlabel.image.resize.ImageResizer;
import com.andrewd.recordlabel.io.IOFunction;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.function.Function;

/**
 * Resizes an image to the target size using Scalr resizer
 */
@Component
public class ImageResizerDefault implements ImageResizer {

    @Autowired
    private IOFunction<InputStream, BufferedImage> bufferedImageFactory;

    @Autowired
    private Function<BufferedImage, Scalr.Mode> resizeModeDeterminer;

    @Autowired
    private ScalrResizer scalrResizer;

    @Autowired
    RenderedImageWriter imageWriter;

    @Value("${" + WebConfig.THUMBNAILS_RESIZE_METHOD_SETTINGS_KEY + "}")
    public Scalr.Method scalingMethod;

    /**
     * Resizes an image so that its longest edge matches the target size.
     * Input ant output streams do not get closed after the operation is
     * completed.
     * @param imageInputStream input stream that contains an image to be resized
     * @param resizedImageOutputStream output stream a resized image is to be
     *                                 written to
     * @param imageFormatName an informal name of the image format
     * @param targetSize target size of the longest edge
     * @throws Exception in case of failure of any sort
     */
    @Override
    public void resizeImage(InputStream imageInputStream,
                            OutputStream resizedImageOutputStream,
                            String imageFormatName,
                            int targetSize) throws Exception {

        BufferedImage image = bufferedImageFactory.apply(imageInputStream);

        Scalr.Mode resizeMode = resizeModeDeterminer.apply(image);

        BufferedImage resizedImage = scalrResizer.resize(image, imageFormatName,
                targetSize, scalingMethod, resizeMode);

        imageWriter.write(resizedImage, imageFormatName, resizedImageOutputStream);
    }
}