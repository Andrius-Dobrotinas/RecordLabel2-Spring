package com.andrewd.recordlabel.data.service;

import com.andrewd.recordlabel.data.model.*;
import com.andrewd.recordlabel.supermodel.ContentBaseSlim;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class EntityToModelTransformerDefault implements EntityToModelTransformer {

    public com.andrewd.recordlabel.supermodel.Release getRelease(Release entity) {
        com.andrewd.recordlabel.supermodel.Release model = new com.andrewd.recordlabel.supermodel.Release();
        model.date = entity.date;
        model.id = entity.id;
        model.printStatus = entity.printStatus;
        model.title = entity.title;
        model.catalogueNumber = entity.catalogueNumber;
        model.date = entity.date;
        model.length = entity.length;
        if (entity.artist != null) {
            model.artist = getArtist(entity.artist);
        }
        if (entity.media != null) {
            model.media = getMediaType(entity.media);
        }
        model.tracks = transformList(entity.tracks, this::getTrack);
        model.images = entity.images;

        transformContentBase(entity, model);
        return model;
    }

    public com.andrewd.recordlabel.supermodel.ReleaseSlim getReleaseSlim(Release entity) {
        com.andrewd.recordlabel.supermodel.ReleaseSlim model = new com.andrewd.recordlabel.supermodel.ReleaseSlim();
        model.date = entity.date;
        model.id = entity.id;
        model.printStatus = entity.printStatus;
        model.title = entity.title;
        model.catalogueNumber = entity.catalogueNumber;
        model.date = entity.date;
        model.length = entity.length;
        if (entity.artist != null) {
            model.artistId = entity.artist.id;
        }
        if (entity.media != null) {
            model.mediaId = entity.media.id;
        }
        model.tracks = transformList(entity.tracks, this::getTrack);

        transformContentBaseSlim(entity, model);
        return model;
    }

    public com.andrewd.recordlabel.supermodel.Artist getArtist(Artist entity) {
        com.andrewd.recordlabel.supermodel.Artist model = new com.andrewd.recordlabel.supermodel.Artist();
        model.id = entity.id;
        model.name = entity.name;
        model.text = entity.text;

        transformContentBase(entity, model);
        return model;
    }

    public com.andrewd.recordlabel.supermodel.ArtistBarebones getArtistBarebones(Artist entity) {
        com.andrewd.recordlabel.supermodel.ArtistBarebones model = new com.andrewd.recordlabel.supermodel.ArtistBarebones();
        model.id = entity.id;
        model.name = entity.name;
        model.text = entity.text;
        return model;
    }

    public com.andrewd.recordlabel.supermodel.Metadata getMetadata(Metadata entity) {
        com.andrewd.recordlabel.supermodel.Metadata model = new com.andrewd.recordlabel.supermodel.Metadata();
        model.id = entity.id;
        model.text = entity.text;
        model.type = entity.type;
        return model;
    }

    public com.andrewd.recordlabel.supermodel.Reference getReference(Reference entity) {
        com.andrewd.recordlabel.supermodel.Reference model = new com.andrewd.recordlabel.supermodel.Reference();
        model.id = entity.id;
        model.target = entity.target;
        model.type = entity.type;
        model.order = entity.order;
        return model;
    }

    public com.andrewd.recordlabel.supermodel.Track getTrack(Track entity) {
        com.andrewd.recordlabel.supermodel.Track model = new com.andrewd.recordlabel.supermodel.Track();
        model.id = entity.id;
        model.reference = entity.reference;
        model.title = entity.title;
        return model;
    }

    public com.andrewd.recordlabel.supermodel.MediaType getMediaType(MediaType entity) {
        com.andrewd.recordlabel.supermodel.MediaType model = new com.andrewd.recordlabel.supermodel.MediaType();
        model.id = entity.id;
        model.text = entity.text;
        return model;
    }

    private <TEntity extends ContentBase, TModel extends com.andrewd.recordlabel.supermodel.ContentBase>
    void transformContentBase(TEntity entity, TModel model) {
        model.metadata = transformList(entity.metadata, this::getMetadata);
        model.references = transformList(entity.references, this::getReference);
    }

    private <TEntity extends ContentBase, TModel extends ContentBaseSlim>
    void transformContentBaseSlim(TEntity entity, TModel model) {
        model.metadataIds = entity.metadata.stream().mapToInt(x -> x.id).boxed().collect(Collectors.toList());
        model.references = transformList(entity.references, this::getReference);
    }

    public <T, Tm> List<Tm> transformList(List<T> list, Function<T, Tm> function) {
        ArrayList<Tm> superModels = new ArrayList<Tm>();
        for (T item : list) {
            superModels.add(function.apply(item));
        }
        return superModels;
    }
}