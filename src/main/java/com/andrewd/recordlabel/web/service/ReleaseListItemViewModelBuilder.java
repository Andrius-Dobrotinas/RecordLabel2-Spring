package com.andrewd.recordlabel.web.service;

import com.andrewd.recordlabel.WebConfig;
import com.andrewd.recordlabel.supermodel.*;
import com.andrewd.recordlabel.web.model.ReleaseListItemViewModel;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class ReleaseListItemViewModelBuilder implements Function<Release, ReleaseListItemViewModel> {

    @Value("${" + WebConfig.IMAGES_VIRTUAL_PATH_SETTINGS_KEY + "}")
    public String imagesVirtualPath;

    @Autowired
    private UrlBuilderFunction urlBuilder;

    @Override
    public ReleaseListItemViewModel apply(Release source) {
        String thumbnailUrl = null;
        if (source.thumbnail != null) {
            thumbnailUrl = urlBuilder.build(imagesVirtualPath, source.thumbnail.fileName);
        }
        return new ReleaseListItemViewModel(source, thumbnailUrl);
    }
}