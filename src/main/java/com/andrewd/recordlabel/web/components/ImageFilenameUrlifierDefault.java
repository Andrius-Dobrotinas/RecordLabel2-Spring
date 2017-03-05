package com.andrewd.recordlabel.web.components;

import com.andrewd.recordlabel.supermodels.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageFilenameUrlifierDefault implements ImageFilenameUrlifier {

    @Autowired
    private UrlBuilderFunction urlBuilder;

    @Override
    public void urlify(Image image, String virtualPath) {
        image.fileName = urlBuilder.build(virtualPath, image.fileName);
    }
}