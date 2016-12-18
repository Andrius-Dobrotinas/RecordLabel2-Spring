package com.andrewd.recordlabel.data.service;

import com.andrewd.recordlabel.supermodel.*;

import java.util.List;

public interface ReleaseService {
    void save(ReleaseSlim model);
    Release getRelease(int id);
    List<MediaType> getMediaTypeList();
    List<Metadata> getMetadataList();
}
