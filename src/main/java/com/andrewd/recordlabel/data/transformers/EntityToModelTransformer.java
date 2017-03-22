package com.andrewd.recordlabel.data.transformers;

import com.andrewd.recordlabel.data.entities.*;

import java.util.*;
import java.util.function.Function;

/**
 * Transforms entities that are stored in the database into domain models.
 * All methods are guaranteed to work with null instances, in which case
 * they return null
 */
public interface EntityToModelTransformer {
    com.andrewd.recordlabel.supermodels.Release getRelease(Release entity);
    com.andrewd.recordlabel.supermodels.ReleaseSlim getReleaseSlim(Release entity);
    com.andrewd.recordlabel.supermodels.Metadata getMetadata(Metadata entity);
    com.andrewd.recordlabel.supermodels.Reference getReference(Reference entity);
    com.andrewd.recordlabel.supermodels.Track getTrack(Track entity);
    com.andrewd.recordlabel.supermodels.MediaType getMediaType(MediaType entity);
    com.andrewd.recordlabel.supermodels.Image getImage(Image entity);
    com.andrewd.recordlabel.supermodels.Thumbnail getThumbnail(Thumbnail entity);
    com.andrewd.recordlabel.supermodels.Artist getArtist(Artist entity);
    com.andrewd.recordlabel.supermodels.ArtistBarebones getArtistBarebones(Artist entity);
    <T, Tm> List<Tm> transformList(List<T> list, Function<T, Tm> function);
}