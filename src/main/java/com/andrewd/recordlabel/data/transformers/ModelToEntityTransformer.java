package com.andrewd.recordlabel.data.transformers;

import com.andrewd.recordlabel.data.entities.*;

import java.util.*;
import java.util.function.Function;

/**
 * Transforms domain models into entity types that are stored in the database.
 * All methods are guaranteed to work with null instances, in which case
 * they return null
 */
public interface ModelToEntityTransformer {
    Release getRelease(com.andrewd.recordlabel.supermodels.ReleaseSlim model);
    Artist getArtist(com.andrewd.recordlabel.supermodels.Artist model);
    Track getTrack(com.andrewd.recordlabel.supermodels.Track model);
    Reference getReference(com.andrewd.recordlabel.supermodels.Reference model);
    Metadata getMetadata(com.andrewd.recordlabel.supermodels.Metadata model);
    MediaType getMediaType(com.andrewd.recordlabel.supermodels.MediaType model);
    Image getImage(com.andrewd.recordlabel.supermodels.Image model);
    Thumbnail getThumbnail(com.andrewd.recordlabel.supermodels.Thumbnail model);

    <TModel, TEntity> List<TEntity>
    transformList(List<TModel> list, Function<TModel, TEntity> transformFunction);
}
