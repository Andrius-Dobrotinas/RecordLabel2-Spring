package com.andrewd.recordlabel.web.service;

import com.andrewd.recordlabel.supermodel.Release;
import com.andrewd.recordlabel.web.model.ReleaseViewModel;

public interface ReleaseViewModelTransformer {
    ReleaseViewModel transform(Release model);
}