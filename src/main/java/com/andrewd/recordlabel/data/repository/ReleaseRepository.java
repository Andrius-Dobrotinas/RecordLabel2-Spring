package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.SortDirection;
import com.andrewd.recordlabel.data.entities.*;

import java.util.List;

public interface ReleaseRepository {
    <T> T save(T entity);
    <T> T[] save(T[] entities);
    Release getRelease(int id);
    List<Release> getReleases(int batchNumber, int batchSize, String orderByProperty, SortDirection direction);

    /* TODO: probably move this out to a separate repository.
    Keeping it here for now in case I decide to implement a generic thing */
    List<Artist> getAllArtists();
    int getTotalReleaseCount();
    List<MediaType> getMediaTypeList();
    List<Metadata> getMetadataList();

    boolean objectExists(int id);
    <T extends ContentBase> T getObject(Class<T> type, int id);
}