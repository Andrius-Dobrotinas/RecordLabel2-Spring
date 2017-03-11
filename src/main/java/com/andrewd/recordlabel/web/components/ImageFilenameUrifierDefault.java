package com.andrewd.recordlabel.web.components;

import com.andrewd.recordlabel.supermodels.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageFilenameUrifierDefault implements ImageFilenameUrifier {

    @Autowired
    private UriBuilderFunction uriBuilder;

    @Override
    public void urlify(Image image, String virtualPath) {
        image.fileName = uriBuilder.build(virtualPath, image.fileName);
    }
}