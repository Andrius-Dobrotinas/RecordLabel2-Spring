package com.andrewd.recordlabel.web.components;

import com.andrewd.recordlabel.WebConfig;
import com.andrewd.recordlabel.supermodels.*;
import com.andrewd.recordlabel.web.models.ReleaseListItemViewModel;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class ReleaseListItemViewModelBuilder implements Function<Release, ReleaseListItemViewModel> {

    @Value("${" + WebConfig.IMAGES_VIRTUAL_PATH_SETTINGS_KEY + "}")
    public String imagesVirtualPath;

    @Autowired
    private UriBuilderFunction uriBuilder;

    @Override
    public ReleaseListItemViewModel apply(Release source) {
        String thumbnailUrl = null;
        if (source.thumbnail != null) {
            thumbnailUrl = uriBuilder.build(imagesVirtualPath, source.thumbnail.fileName);
        }
        return new ReleaseListItemViewModel(source, thumbnailUrl);
    }
}