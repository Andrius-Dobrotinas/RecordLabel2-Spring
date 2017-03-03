package com.andrewd.recordlabel.web.service;

import com.andrewd.recordlabel.WebConfig;
import com.andrewd.recordlabel.supermodel.*;
import com.andrewd.recordlabel.web.model.ReleaseListItemViewModel;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;

@Component
public class ReleaseListItemViewModelBuilderDefault implements ReleaseListItemViewModelBuilder {

    @Value("${" + WebConfig.IMAGES_VIRTUAL_PATH_SETTINGS_KEY + "}")
    public String imagesVirtualPath;

    @Override
    public ReleaseListItemViewModel build(Release source) {

        Image image =  source.images.stream()
                .filter(i -> i.isThumbnail == true)
                .findFirst().orElse(null);

        String thumbnail = null;
        if (image != null) {
            thumbnail = imagesVirtualPath + image.fileName;
        }
        return new ReleaseListItemViewModel(source, thumbnail);
    }
}