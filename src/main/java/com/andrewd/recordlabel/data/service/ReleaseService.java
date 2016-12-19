package com.andrewd.recordlabel.data.service;

import com.andrewd.recordlabel.common.BatchedResult;
import com.andrewd.recordlabel.supermodel.*;

import java.util.List;

public interface ReleaseService {
    void save(ReleaseSlim model);
    Release getRelease(int id);
    ReleaseSlim getReleaseSlim(int id);
    BatchedResult<Release> getReleases(int batchNumber, int batchSize);
    List<MediaType> getMediaTypeList();
    List<Metadata> getMetadataList();
    List<ArtistBarebones> getArtistBarebonesList();
}
