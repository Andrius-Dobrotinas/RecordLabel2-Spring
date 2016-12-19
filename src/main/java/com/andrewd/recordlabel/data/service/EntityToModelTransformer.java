package com.andrewd.recordlabel.data.service;

import com.andrewd.recordlabel.data.model.*;

import java.util.*;
import java.util.function.Function;

public interface EntityToModelTransformer {
    com.andrewd.recordlabel.supermodel.Release getRelease(Release entity);
    com.andrewd.recordlabel.supermodel.ReleaseSlim getReleaseSlim(Release entity);
    com.andrewd.recordlabel.supermodel.Metadata getMetadata(Metadata entity);
    com.andrewd.recordlabel.supermodel.Reference getReference(Reference entity);
    com.andrewd.recordlabel.supermodel.Track getTrack(Track entity);
    com.andrewd.recordlabel.supermodel.MediaType getMediaType(MediaType entity);
    com.andrewd.recordlabel.supermodel.Artist getArtist(Artist entity);
    <T, Tm> List<Tm> transformList(List<T> list, Function<T, Tm> function);
}