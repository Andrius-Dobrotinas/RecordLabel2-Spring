package com.andrewd.recordlabel.data.service;

import com.andrewd.recordlabel.data.model.*;

import java.util.*;
import java.util.function.Function;

public interface ModelToEntityTransformer {
    Release getRelease(com.andrewd.recordlabel.supermodel.ReleaseSlim model);
    Artist getArtist(com.andrewd.recordlabel.supermodel.Artist model);
    Track getTrack(com.andrewd.recordlabel.supermodel.Track model);
    Reference getReference(com.andrewd.recordlabel.supermodel.Reference model);
    Metadata getMetadata(com.andrewd.recordlabel.supermodel.Metadata model);
    MediaType getMediaType(com.andrewd.recordlabel.supermodel.MediaType model);
    <TModel, TEntity> List<TEntity> transformList(List<TModel> list, Function<TModel, TEntity> transformFunction);
}
