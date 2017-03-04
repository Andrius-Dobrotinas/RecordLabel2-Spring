package com.andrewd.recordlabel.data.services;

import com.andrewd.recordlabel.common.BatchedResult;
import com.andrewd.recordlabel.data.entities.Image;
import com.andrewd.recordlabel.supermodels.*;

import java.util.List;

public interface ReleaseService {
    void save(ReleaseSlim model);
    Release getRelease(int id);
    ReleaseSlim getReleaseSlim(int id);
    BatchedResult<Release> getReleases(int batchNumber, int batchSize);
    List<MediaType> getMediaTypeList();
    List<Metadata> getMetadataList();
    List<ArtistBarebones> getArtistBarebonesList();

    boolean objectExists(int id);
    <T> T getObject(Class<T> type, int id);
    void save(Image[] images);
}
