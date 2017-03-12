package com.andrewd.recordlabel.image;

import org.springframework.stereotype.Component;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;

/**
 * Writes a rendered image to the provided output stream using
 * ImageIO.write
 */
@Component
public class RenderedImageWriterDefault implements RenderedImageWriter {

    /**
     * Writes an image using an arbitrary <code>ImageWriter</code>
     * that supports the given format to an <code>OutputStream</code>.
     *
     * <p> This method <em>does not</em> close the provided
     * <code>OutputStream</code> after the write operation has completed;
     * it is the responsibility of the caller to close the stream, if desired.
     *
     * <p> The current cache settings from <code>getUseCache</code>and
     * <code>getCacheDirectory</code> will be used to control caching.
     */
    @Override
    public void write(RenderedImage image,
                         String formatName,
                         OutputStream outputStream)

            throws IOException, AppropriateWriterNotFoundException
    {
        if (!ImageIO.write(image, formatName, outputStream))
            throw new AppropriateWriterNotFoundException(formatName);
    }
}