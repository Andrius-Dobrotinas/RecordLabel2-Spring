package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.model.*;

import java.util.List;

public interface ReleaseRepository {
    <T> T save(T entity);
    Release getRelease(int id);
    List<Release> getAllReleases();

    /* TODO: probably move this out to a separate repository.
    Keeping it here for now in case I decide to implement a generic thing */
    List<Artist> getAllArtists();
    int getTotalReleaseCount();
    List<MediaType> getMediaTypeList();
    List<Metadata> getMetadataList();
}