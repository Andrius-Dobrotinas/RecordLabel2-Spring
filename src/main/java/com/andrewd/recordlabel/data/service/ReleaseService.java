package com.andrewd.recordlabel.data.service;

import com.andrewd.recordlabel.data.model.Release;
import com.andrewd.recordlabel.supermodel.MediaType;

import java.util.List;

public interface ReleaseService {
    void save(Release entity); // TODO: change to super model
    com.andrewd.recordlabel.supermodel.Release getRelease(int id);
    List<MediaType> getMediaTypeList();
}
