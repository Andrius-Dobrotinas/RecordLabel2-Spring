package com.andrewd.recordlabel.data.transformers;

import com.andrewd.recordlabel.data.entities.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
public class ModelToEntityTransformerDefault implements ModelToEntityTransformer {

    @Override
    public Release getRelease(com.andrewd.recordlabel.supermodels.ReleaseSlim model) {
        if (model == null)
            return null;

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

    @Override
    public Artist getArtist(com.andrewd.recordlabel.supermodels.Artist model) {
        if (model == null)
            return null;

        Artist entity = new Artist();
        entity.id = model.id;
        entity.text = model.text;
        entity.name = model.name;

        transformContentBase(model, entity);
        return entity;
    }

    @Override
    public Track getTrack(com.andrewd.recordlabel.supermodels.Track model) {
        if (model == null)
            return null;

        Track entity = new Track();
        entity.id = model.id;
        entity.title = model.title;
        entity.reference = model.reference;
        return entity;
    }

    @Override
    public Reference getReference(com.andrewd.recordlabel.supermodels.Reference model) {
        if (model == null)
            return null;

        Reference entity = new Reference();
        entity.id = model.id;
        entity.target = model.target;
        entity.order = model.order;
        entity.type = model.type;
        return entity;
    }

    @Override
    public Metadata getMetadata(com.andrewd.recordlabel.supermodels.Metadata model) {
        if (model == null)
            return null;

        Metadata entity = new Metadata();
        entity.id = model.id;
        entity.type = model.type;
        entity.text = model.text;
        return entity;
    }

    @Override
    public MediaType getMediaType(com.andrewd.recordlabel.supermodels.MediaType model) {
        if (model == null)
            return null;

        MediaType entity = new MediaType();
        entity.id = model.id;
        entity.text = model.text;
        return entity;
    }

    @Override
    public Image getImage(com.andrewd.recordlabel.supermodels.Image model) {
        if (model == null)
            return null;

        Image entity = new Image();
        entity.id = model.id;
        entity.fileName = model.fileName;
        return entity;
    }

    @Override
    public Thumbnail getThumbnail(com.andrewd.recordlabel.supermodels.Thumbnail model) {
        if (model == null)
            return null;

        Thumbnail entity = new Thumbnail();
        entity.id = model.id;
        entity.fileName = model.fileName;
        return entity;
    }

    private <TModel extends com.andrewd.recordlabel.supermodels.ContentBase,
            TEntity extends ContentBase>
    void transformContentBase(TModel model, TEntity entity) {
        entity.metadata = transformList(model.metadata, this::getMetadata);
        entity.references = transformList(model.references, this::getReference);
    }

    @Override
    public <TModel, TEntity> List<TEntity> transformList(
            List<TModel> list, Function<TModel, TEntity> transformFunction)
    {
        if (list == null)
            return null;

        List<TEntity> result = new ArrayList<>();
        for(TModel model : list) {
            result.add(transformFunction.apply(model));
        }
        return result;
    }
}