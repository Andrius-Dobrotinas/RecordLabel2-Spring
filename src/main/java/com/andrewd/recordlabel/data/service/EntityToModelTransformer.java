package com.andrewd.recordlabel.data.service;

import com.andrewd.recordlabel.data.model.*;
import com.andrewd.recordlabel.data.model.Artist;
import com.andrewd.recordlabel.data.model.Content;
import com.andrewd.recordlabel.data.model.Metadata;
import com.andrewd.recordlabel.data.model.Reference;
import com.andrewd.recordlabel.data.model.Release;

import java.util.*;
import java.util.function.Function;

public class EntityToModelTransformer implements IEntityToModelTransformer {

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
        model.tracks = transformList(entity.tracks, this::getTrack);
        transformContent(entity, model);
        return model;
    }

    public com.andrewd.recordlabel.supermodel.Artist getArtist(Artist entity) {
        com.andrewd.recordlabel.supermodel.Artist model = new com.andrewd.recordlabel.supermodel.Artist();
        model.id = entity.id;
        model.name = entity.name;
        model.text = entity.text;

        transformContent(entity, model);
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

    private <TEntity extends Content, TModel extends com.andrewd.recordlabel.supermodel.Content>
    void transformContent(TEntity entity, TModel model) {
        model.metadata = transformList(entity.metadata, this::getMetadata);
        model.references = transformList(entity.references, this::getReference);
    }

    public <T, Tm> ArrayList<Tm> transformList(List<T> list, Function<T, Tm> function) {
        ArrayList<Tm> superModels = new ArrayList<Tm>();
        for (T item : list) {
            superModels.add(function.apply(item));
        }
        return superModels;
    }
}