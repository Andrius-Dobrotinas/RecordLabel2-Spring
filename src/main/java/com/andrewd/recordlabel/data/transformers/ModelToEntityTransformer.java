package com.andrewd.recordlabel.data.transformers;

import com.andrewd.recordlabel.data.entities.*;

import java.util.*;
import java.util.function.Function;

public interface ModelToEntityTransformer {
    Release getRelease(com.andrewd.recordlabel.supermodels.ReleaseSlim model);
    Artist getArtist(com.andrewd.recordlabel.supermodels.Artist model);
    Track getTrack(com.andrewd.recordlabel.supermodels.Track model);
    Reference getReference(com.andrewd.recordlabel.supermodels.Reference model);
    Metadata getMetadata(com.andrewd.recordlabel.supermodels.Metadata model);
    MediaType getMediaType(com.andrewd.recordlabel.supermodels.MediaType model);
    <TModel, TEntity> List<TEntity> transformList(List<TModel> list, Function<TModel, TEntity> transformFunction);
}
