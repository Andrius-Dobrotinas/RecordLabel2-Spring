package com.andrewd.recordlabel.service;

import com.andrewd.recordlabel.common.*;
import com.andrewd.recordlabel.data.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class EntityToModelTransformerTests {

    @Test
    public void transformRelease() {
        Release entity = new Release();

        entity.id = 1;
        entity.date = 1973;
        entity.catalogueNumber = "KC3000";
        entity.length = 43;
        entity.printStatus = PrintStatus.InPrint;
        entity.title = "Raw Power";

        EntityToModelTransformer transformer = new EntityToModelTransformer();
        com.andrewd.recordlabel.supermodel.Release model = transformer.getRelease(entity);
    }

    private void verifyRelease(Release entity, com.andrewd.recordlabel.supermodel.Release model) {
        Assert.assertEquals(entity.id, model.id);
        Assert.assertEquals(entity.date, model.date);
        Assert.assertEquals(entity.catalogueNumber, model.catalogueNumber);
        Assert.assertEquals(entity.length, model.length);
        Assert.assertEquals(entity.printStatus, model.printStatus);
        Assert.assertEquals(entity.title, model.title);
    }

    @Test
    public void transformRelease_MakeSureArtistIsTransformedToo() {
        Release entity = new Release();
        entity.artist = getArtistEntity();

        EntityToModelTransformer transformer = new EntityToModelTransformer();
        com.andrewd.recordlabel.supermodel.Release model = transformer.getRelease(entity);

        Assert.assertNotNull("Must transform release artist too", model.artist);
    }

    @Test
    public void transformRelease_MakeSureMetadataIsTransformedToo() {
        Release entity = new Release();
        entity.metadata.add(getMetadata());

        EntityToModelTransformer transformer = new EntityToModelTransformer();
        com.andrewd.recordlabel.supermodel.Release model = transformer.getRelease(entity);

        Assert.assertNotNull("Must transform release metadata too", model.metadata);
        Assert.assertEquals("Must transform release metadata and add it to the list", 1, model.metadata.size());
    }

    @Test
    public void transformListOfEntities() {
        List<Release> list = new ArrayList<Release>();
        Release entity1 = new Release();
        list.add(entity1);

        EntityToModelTransformer transformer = new EntityToModelTransformer();
        List<com.andrewd.recordlabel.supermodel.Release> results =
                transformer.transformList(list, transformer::getRelease);

        Assert.assertEquals(1, results.size());

        com.andrewd.recordlabel.supermodel.Release result1 = results.get(0);
        verifyRelease(entity1, result1);
    }

    @Test
    public void transformArtist() {
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
    public void transformArtist_MakeSureMetadataIsTransformedToo() {
        Artist entity = new Artist();
        entity.metadata.add(getMetadata());

        EntityToModelTransformer transformer = new EntityToModelTransformer();
        com.andrewd.recordlabel.supermodel.Artist model = transformer.getArtist(entity);

        Assert.assertNotNull("Must transform artist metadata too", model.metadata);
        Assert.assertEquals("Must transform artist metadata and add it to the list", 1, model.metadata.size());
    }

    @Test
    public void transformMetadata() {
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