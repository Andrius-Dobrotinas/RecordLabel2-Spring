package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.model.*;

import java.util.List;

public interface ReleaseRepository {
    void save(Release entity);
    Release getRelease(int id);
    List<MediaType> getMediaTypeList();
}
