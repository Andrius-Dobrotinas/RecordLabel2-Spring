package com.andrewd.recordlabel.data.service;

import com.andrewd.recordlabel.data.model.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
public class ModelToEntityTransformerDefault implements ModelToEntityTransformer {

    public Release getRelease(com.andrewd.recordlabel.supermodel.ReleaseSlim model) {
        Release entity = new Release();
        entity.id = model.id;
        entity.printStatus = model.printStatus;
        entity.length = model.length;
        entity.catalogueNumber = model.catalogueNumber;
        entity.date = model.date;
        entity.title = model.title;

        entity.tracks = transformList(model.tracks, this::getTrack);
        entity.references = transformList(model.references, this::getReference);

        for(int id : model.metadataIds) {
            Metadata metadata = new Metadata();
            metadata.id = id;
            entity.metadata.add(metadata);
        }

        Artist artist = new Artist();
        artist.id = model.artistId;
        entity.artist = artist;

        MediaType media = new MediaType();
        media.id = model.mediaId;
        entity.media = media;

        return entity;
    }

    public Artist getArtist(com.andrewd.recordlabel.supermodel.Artist model) {
        Artist entity = new Artist();
        entity.id = model.id;
        entity.text = model.text;
        entity.name = model.name;

        transformContentBase(model, entity);
        return entity;
    }

    public Track getTrack(com.andrewd.recordlabel.supermodel.Track model) {
        Track entity = new Track();
        entity.id = model.id;
        entity.title = model.title;
        entity.reference = model.reference;
        return entity;
    }

    public Reference getReference(com.andrewd.recordlabel.supermodel.Reference model) {
        Reference entity = new Reference();
        entity.id = model.id;
        entity.target = model.target;
        entity.order = model.order;
        entity.type = model.type;
        return entity;
    }

    public Metadata getMetadata(com.andrewd.recordlabel.supermodel.Metadata model) {
        Metadata entity = new Metadata();
        entity.id = model.id;
        entity.type = model.type;
        entity.text = model.text;
        return entity;
    }

    public MediaType getMediaType(com.andrewd.recordlabel.supermodel.MediaType model) {
        MediaType entity = new MediaType();
        entity.id = model.id;
        entity.text = model.text;
        return entity;
    }

    private <TModel extends com.andrewd.recordlabel.supermodel.ContentBase, TEntity extends ContentBase>
    void transformContentBase(TModel model, TEntity entity) {
        entity.metadata = transformList(model.metadata, this::getMetadata);
        entity.references = transformList(model.references, this::getReference);
    }

    public <TModel, TEntity> List<TEntity> transformList(List<TModel> list, Function<TModel, TEntity> transformFunction) {
        List<TEntity> result = new ArrayList<>();
        for(TModel model : list) {
            result.add(transformFunction.apply(model));
        }
        return result;
    }
}