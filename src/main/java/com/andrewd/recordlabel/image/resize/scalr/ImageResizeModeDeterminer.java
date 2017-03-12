package com.andrewd.recordlabel.image.resize.scalr;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;
import java.awt.image.BufferedImage;
import java.util.function.Function;

/**
 * Determines Scalr resize mode for the supplied image by
 * evaluating image width and height and choosing the mode
 * based on whichever is greater
 */
@Component
public class ImageResizeModeDeterminer
        implements Function<BufferedImage, Scalr.Mode> {

    /**
     * Determines Scalr resize mode for the supplied image by
     * evaluating image width and height and choosing the mode
     * based on whichever is greater
     * @param image image whose resize mode is to be determined
     * @return resize mode value
     */
    @Override
    public Scalr.Mode apply(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        if (width == height)
            return Scalr.Mode.FIT_EXACT;

        return (width > height)
                ? Scalr.Mode.FIT_TO_WIDTH
                : Scalr.Mode.FIT_TO_HEIGHT;
    }
}