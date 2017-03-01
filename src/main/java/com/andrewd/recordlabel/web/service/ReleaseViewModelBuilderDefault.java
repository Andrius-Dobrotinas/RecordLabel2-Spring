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
    public ReleaseViewModel build(Release model) {
        ReleaseViewModel result = new ReleaseViewModel();
        result.release = model;

        if (model.references.size() > 0) {
            result.youtubeReferences = model.references.stream()
                    .filter(x -> (x.type == ReferenceType.Youtube) ? true : false)
                    .collect(Collectors.toList());

            model.references = model.references.stream()
                    .filter(x -> (x.type != ReferenceType.Youtube) ? true : false)
                    .collect(Collectors.toList());
        }
        else {
            result.youtubeReferences = new ArrayList<>();
        }

        return result;
    }
}