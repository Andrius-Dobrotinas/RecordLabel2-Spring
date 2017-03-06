package com.andrewd.recordlabel.data.transformers;

import com.andrewd.recordlabel.data.entities.*;
import com.andrewd.recordlabel.supermodels.ContentBaseSlim;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class EntityToModelTransformerDefault implements EntityToModelTransformer {

    @Override
    public com.andrewd.recordlabel.supermodels.Release getRelease(Release entity) {
        com.andrewd.recordlabel.supermodels.Release model = new com.andrewd.recordlabel.supermodels.Release();
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
        model.images = transformList(entity.images, this::getImage);

        if (entity.thumbnail != null) {
            model.thumbnail = getThumbnail(entity.thumbnail);
        }

        transformContentBase(entity, model);
        return model;
    }

    @Override
    public com.andrewd.recordlabel.supermodels.ReleaseSlim getReleaseSlim(Release entity) {
        com.andrewd.recordlabel.supermodels.ReleaseSlim model = new com.andrewd.recordlabel.supermodels.ReleaseSlim();
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

    @Override
    public com.andrewd.recordlabel.supermodels.Artist getArtist(Artist entity) {
        com.andrewd.recordlabel.supermodels.Artist model = new com.andrewd.recordlabel.supermodels.Artist();
        model.id = entity.id;
        model.name = entity.name;
        model.text = entity.text;

        transformContentBase(entity, model);
        return model;
    }

    @Override
    public com.andrewd.recordlabel.supermodels.ArtistBarebones getArtistBarebones(Artist entity) {
        com.andrewd.recordlabel.supermodels.ArtistBarebones model = new com.andrewd.recordlabel.supermodels.ArtistBarebones();
        model.id = entity.id;
        model.name = entity.name;
        model.text = entity.text;
        return model;
    }

    @Override
    public com.andrewd.recordlabel.supermodels.Metadata getMetadata(Metadata entity) {
        com.andrewd.recordlabel.supermodels.Metadata model = new com.andrewd.recordlabel.supermodels.Metadata();
        model.id = entity.id;
        model.text = entity.text;
        model.type = entity.type;
        return model;
    }

    @Override
    public com.andrewd.recordlabel.supermodels.Reference getReference(Reference entity) {
        com.andrewd.recordlabel.supermodels.Reference model = new com.andrewd.recordlabel.supermodels.Reference();
        model.id = entity.id;
        model.target = entity.target;
        model.type = entity.type;
        model.order = entity.order;
        return model;
    }

    @Override
    public com.andrewd.recordlabel.supermodels.Track getTrack(Track entity) {
        com.andrewd.recordlabel.supermodels.Track model = new com.andrewd.recordlabel.supermodels.Track();
        model.id = entity.id;
        model.reference = entity.reference;
        model.title = entity.title;
        return model;
    }

    @Override
    public com.andrewd.recordlabel.supermodels.MediaType getMediaType(MediaType entity) {
        com.andrewd.recordlabel.supermodels.MediaType model = new com.andrewd.recordlabel.supermodels.MediaType();
        model.id = entity.id;
        model.text = entity.text;
        return model;
    }

    @Override
    public com.andrewd.recordlabel.supermodels.Image getImage(Image entity) {
        com.andrewd.recordlabel.supermodels.Image model = new com.andrewd.recordlabel.supermodels.Image();
        model.id = entity.id;
        model.fileName = entity.fileName;
        return model;
    }

    @Override
    public com.andrewd.recordlabel.supermodels.Thumbnail getThumbnail(Thumbnail entity) {
        com.andrewd.recordlabel.supermodels.Thumbnail model = new com.andrewd.recordlabel.supermodels.Thumbnail();
        model.id = entity.id;
        model.fileName = entity.fileName;
        return model;
    }

    private <TEntity extends ContentBase, TModel extends com.andrewd.recordlabel.supermodels.ContentBase>
    void transformContentBase(TEntity entity, TModel model) {
        model.metadata = transformList(entity.metadata, this::getMetadata);
        model.references = transformList(entity.references, this::getReference);
    }

    private <TEntity extends ContentBase, TModel extends ContentBaseSlim>
    void transformContentBaseSlim(TEntity entity, TModel model) {
        model.metadataIds = entity.metadata.stream().mapToInt(x -> x.id).boxed().collect(Collectors.toList());
        model.references = transformList(entity.references, this::getReference);
    }

    @Override
    public <T, Tm> List<Tm> transformList(List<T> list, Function<T, Tm> function) {
        ArrayList<Tm> superModels = new ArrayList<Tm>();
        for (T item : list) {
            superModels.add(function.apply(item));
        }
        return superModels;
    }
}