package com.andrewd.recordlabel.service;

import com.andrewd.recordlabel.data.service.EntityToModelTransformer;
import com.andrewd.recordlabel.common.*;
import com.andrewd.recordlabel.data.model.*;
import com.sun.media.jfxmedia.Media;
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
    public void transformRelease_MakeSureMediaTypeIsTransformedToo() {
        Release entity = new Release();
        entity.media = getMediaType();

        EntityToModelTransformer transformer = new EntityToModelTransformer();
        com.andrewd.recordlabel.supermodel.Release model = transformer.getRelease(entity);

        Assert.assertNotNull("Must transform release media too", model.metadata);
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
    public void transformRelease_MakeSureTracksAreTransformedToo() {
        Release entity = new Release();
        entity.tracks.add(getTrack());

        EntityToModelTransformer transformer = new EntityToModelTransformer();
        com.andrewd.recordlabel.supermodel.Release model = transformer.getRelease(entity);

        Assert.assertNotNull("Must transform release tracks too", model.tracks);
    }

    @Test
    public void transformRelease_MakeSureReferencesAreTransformedToo() {
        Release entity = new Release();
        entity.references.add(getReference());

        EntityToModelTransformer transformer = new EntityToModelTransformer();
        com.andrewd.recordlabel.supermodel.Release model = transformer.getRelease(entity);

        Assert.assertNotNull("Must transform release references too", model.references);
    }

    @Test
    public void transformArtist_MakeSureReferencesAreTransformedToo() {
        Artist entity = new Artist();
        entity.references.add(getReference());

        EntityToModelTransformer transformer = new EntityToModelTransformer();
        com.andrewd.recordlabel.supermodel.Artist model = transformer.getArtist(entity);

        Assert.assertNotNull("Must transform artist references too", model.references);
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

    @Test
    public void transformReference() {
        Reference entity = getReference();

        EntityToModelTransformer transformer = new EntityToModelTransformer();
        com.andrewd.recordlabel.supermodel.Reference model = transformer.getReference(entity);

        Assert.assertEquals(entity.id, model.id);
        Assert.assertEquals(entity.target, model.target);
        Assert.assertEquals(entity.type, model.type);
        Assert.assertEquals(entity.order, model.order);
    }

    @Test
    public void transformTrack() {
        Track entity = getTrack();

        EntityToModelTransformer transformer = new EntityToModelTransformer();
        com.andrewd.recordlabel.supermodel.Track model = transformer.getTrack(entity);

        Assert.assertEquals(entity.id, model.id);
        Assert.assertEquals(entity.reference, model.reference);
        Assert.assertEquals(entity.title, model.title);
    }

    @Test
    public void transformMediaType() {
        MediaType entity = getMediaType();

        EntityToModelTransformer transformer = new EntityToModelTransformer();
        com.andrewd.recordlabel.supermodel.MediaType model = transformer.getMediaType(entity);

        Assert.assertEquals(entity.id, model.id);
        Assert.assertEquals(entity.text, model.text);
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

    private Reference getReference() {
        Reference entity = new Reference();
        entity.id = 1;
        entity.target = "https://www.youtube.com/v/BJIqnXTqg8I";
        entity.type = ReferenceType.Youtube;
        entity.order = 2;
        return entity;
    }

    private Track getTrack() {
        Track entity = new Track();
        entity.id = 1;
        entity.reference = "https://www.youtube.com/watch?v=BJIqnXTqg8I";
        entity.title = "Search And Destroy";
        return entity;
    }

    private MediaType getMediaType() {
        MediaType entity = new MediaType();
        entity.id = 1;
        entity.text = "LP";
        return entity;
    }
}