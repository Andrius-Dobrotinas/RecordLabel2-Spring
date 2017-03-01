package com.andrewd.recordlabel.web.service;

import com.andrewd.recordlabel.supermodel.Release;
import com.andrewd.recordlabel.web.model.ReleaseListItemViewModel;

@FunctionalInterface
public interface ReleaseListItemViewModelBuilder {
    ReleaseListItemViewModel build(Release source);
}