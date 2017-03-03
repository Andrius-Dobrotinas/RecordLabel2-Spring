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

    @Autowired
    public UrlBuilderFunction urlBuilder;

    @Override
    public ReleaseListItemViewModel build(Release source) {
        String thumbnailUrl = null;
        if (source.thumbnail != null) {
            thumbnailUrl = urlBuilder.build(imagesVirtualPath, source.thumbnail.fileName);
        }
        return new ReleaseListItemViewModel(source, thumbnailUrl);
    }
}