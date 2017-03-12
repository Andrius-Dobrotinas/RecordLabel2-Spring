package com.andrewd.recordlabel.image;

import java.awt.image.RenderedImage;
import java.io.*;

/**
 * Writes a rendered image to the provided output stream
 */
public interface RenderedImageWriter {

    /**
     * Writes a rendered image to the provided output stream
     * @param image a <code>RenderedImage</code> to be written.
     * @param formatName a <code>String</code> containing the informal
     * name of the format.
     * @param outputStream an <code>OutputStream</code> to be written to.
     * @throws IOException if an error occurs during writing.
     * @throws AppropriateWriterNotFoundException if no appropriate
     * writer is found.
     */
    void write(RenderedImage image,
                  String formatName,
                  OutputStream outputStream)

            throws IOException, AppropriateWriterNotFoundException;
}