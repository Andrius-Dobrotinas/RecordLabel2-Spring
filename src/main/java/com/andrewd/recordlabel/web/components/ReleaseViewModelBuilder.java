package com.andrewd.recordlabel.web.components;

import com.andrewd.recordlabel.WebConfig;
import com.andrewd.recordlabel.common.ReferenceType;
import com.andrewd.recordlabel.supermodels.*;
import com.andrewd.recordlabel.web.models.ReleaseViewModel;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ReleaseViewModelBuilder implements Function<Release, ReleaseViewModel> {

    @Value("${" + WebConfig.IMAGES_VIRTUAL_PATH_SETTINGS_KEY + "}")
    public String imagesVirtualPath;

    @Autowired
    private ImageFilenameUrifier imgUrifier;

    @Override
    public ReleaseViewModel apply(Release source) {
        List<Reference> references = null;
        List<Reference> youtubeReferences = null;

        if (source.references != null && source.references.size() > 0) {
            youtubeReferences = source.references.stream()
                    .filter(x -> x.type == ReferenceType.Youtube)
                    .collect(Collectors.toList());

            references = source.references.stream()
                    .filter(x -> x.type != ReferenceType.Youtube)
                    .collect(Collectors.toList());
        }

        if (source.images != null && source.images.size() > 0) {
            source.images
                    .forEach(image -> imgUrifier
                            .urlify(image, imagesVirtualPath));
        }

        ReleaseViewModel result = new ReleaseViewModel();
        result.id = source.id;
        result.artist = source.artist;
        result.media = source.media;
        result.title = source.title;
        result.text = source.text;
        result.date = source.date;
        result.length = source.length;
        result.catalogueNumber = source.catalogueNumber;
        result.printStatus = source.printStatus;
        result.tracks = source.tracks;
        result.metadata = source.metadata;
        result.references = references;
        result.youtubeReferences = youtubeReferences;
        result.images = source.images;

        return result;
    }
}