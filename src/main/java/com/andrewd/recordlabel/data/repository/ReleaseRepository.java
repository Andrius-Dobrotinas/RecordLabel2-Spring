package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.model.*;

import java.util.List;

public interface ReleaseRepository {
    Release save(Release entity);
    Release getRelease(int id);
    List<Release> getAllReleases();
    int getTotalReleaseCount();
    List<MediaType> getMediaTypeList();
    List<Metadata> getMetadataList();
}
