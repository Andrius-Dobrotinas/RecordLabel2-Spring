package com.andrewd.recordlabel.service;

import com.andrewd.recordlabel.data.model.*;
import java.util.*;
import java.util.*;
import java.util.function.Function;

public class EntityToModelTransformer {

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
        model.metadata = transformList(entity.metadata, this::getMetadata);
        return model;
    }

    public com.andrewd.recordlabel.supermodel.Artist getArtist(Artist entity) {
        com.andrewd.recordlabel.supermodel.Artist model = new com.andrewd.recordlabel.supermodel.Artist();
        model.id = entity.id;
        model.name = entity.name;
        model.text = entity.text;
        model.metadata = transformList(entity.metadata, this::getMetadata);
        return model;
    }

    public com.andrewd.recordlabel.supermodel.Metadata getMetadata(Metadata entity) {
        com.andrewd.recordlabel.supermodel.Metadata model = new com.andrewd.recordlabel.supermodel.Metadata();
        model.id = entity.id;
        model.text = entity.text;
        model.type = entity.type;
        return model;
    }

    public <T, Tm> ArrayList<Tm> transformList(List<T> list, Function<T, Tm> function) {
        ArrayList<Tm> superModels = new ArrayList<Tm>();
        for (T item : list) {
            superModels.add(function.apply(item));
        }
        return superModels;
    }
}