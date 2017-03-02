package com.andrewd.recordlabel.web.service;

import com.andrewd.recordlabel.common.ReferenceType;
import com.andrewd.recordlabel.supermodel.*;
import com.andrewd.recordlabel.web.model.ReleaseViewModel;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ReleaseViewModelBuilderDefault implements ReleaseViewModelBuilder {

    @Override
    public ReleaseViewModel build(Release source) {
        List<Reference> references = null;
        List<Reference> youtubeReferences = null;

        if (source.references != null && source.references.size() > 0) {
            youtubeReferences = source.references.stream()
                    .filter(x -> (x.type == ReferenceType.Youtube) ? true : false)
                    .collect(Collectors.toList());

            references = source.references.stream()
                    .filter(x -> (x.type != ReferenceType.Youtube) ? true : false)
                    .collect(Collectors.toList());
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
        //result.images = source.images; // TODO: need a view model for Image type
        result.references = references;
        result.youtubeReferences = youtubeReferences;

        return result;
    }
}