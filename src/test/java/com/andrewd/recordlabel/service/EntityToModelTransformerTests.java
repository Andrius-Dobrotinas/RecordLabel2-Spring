package com.andrewd.recordlabel.service;

import com.andrewd.recordlabel.common.*;
import com.andrewd.recordlabel.data.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class EntityToModelTransformerTests {

    @Test
    public void TransformRelease() {
        Release entity = new Release();

        entity.id = 1;
        entity.date = 1973;
        entity.catalogueNumber = "KC3000";
        entity.length = 43;
        entity.printStatus = PrintStatus.InPrint;
        entity.title = "Raw Power";

        EntityToModelTransformer transformer = new EntityToModelTransformer();
        com.andrewd.recordlabel.supermodel.Release model = transformer.getRelease(entity);

        Assert.assertEquals(entity.id, model.id);
        Assert.assertEquals(entity.date, model.date);
        Assert.assertEquals(entity.catalogueNumber, model.catalogueNumber);
        Assert.assertEquals(entity.length, model.length);
        Assert.assertEquals(entity.printStatus, model.printStatus);
        Assert.assertEquals(entity.title, model.title);
    }

    @Test
    public void TransformRelease_Artist() {
        Release entity = new Release();
        entity.artist = getArtistEntity();

        EntityToModelTransformer transformer = new EntityToModelTransformer();
        com.andrewd.recordlabel.supermodel.Release model = transformer.getRelease(entity);

        Assert.assertNotNull("Must transform release artist too", model.artist);
    }

   /* @Test
    public void TransformRelease_Metadata() {
        Release entity = new Release();
        entity.metadata = new ArrayList<Metadata>();
        entity.metadata.add(getMetadata());

        EntityToModelTransformer transformer = new EntityToModelTransformer();
        com.andrewd.recordlabel.supermodel.Release model = transformer.getRelease(entity);

        Assert.assertEquals("Must transform release metadata too", 1, model.metadata.size());
    }*/

    @Test
    public void TransformArtist() {
        Artist entity = getArtistEntity();

        entity.id = 1;
        entity.name = "Iggy & The Stooges";
        entity.text = "description";

        EntityToModelTransformer transformer = new EntityToModelTransformer();
        com.andrewd.recordlabel.supermodel.Artist model = transformer.getArtist(entity);

        Assert.assertEquals(entity.id, model.id);
        Assert.assertEquals(entity.name, model.name);
        Assert.assertEquals(entity.text, model.text);
    }

    @Test
    public void TransformMetadata() {
        Metadata entity = getMetadata();

        EntityToModelTransformer transformer = new EntityToModelTransformer();
        com.andrewd.recordlabel.supermodel.Metadata model = transformer.getMetadata(entity);

        Assert.assertEquals(entity.id, model.id);
        Assert.assertEquals(entity.text, model.text);
        Assert.assertEquals(entity.type, model.type);
    }

    private Artist getArtistEntity() {
        Artist entity = new Artist();
        entity.id = 1;
        entity.name = "Iggy & The Stooges";
        entity.text = "description";
        return entity;
    }

    private Metadata getMetadata() {
        Metadata entity = new Metadata();
        entity.id = 1;
        entity.text = "Rock";
        entity.type = MetadataType.Genre;
        return entity;
    }
}