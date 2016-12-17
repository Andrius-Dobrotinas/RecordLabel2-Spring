package com.andrewd.recordlabel.service;

import com.andrewd.recordlabel.common.*;
import com.andrewd.recordlabel.data.model.*;
import com.andrewd.recordlabel.data.service.ModelToEntityTransformerDefault;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class ModelToEntityTransformerDefaultTests
{
    private ModelToEntityTransformerDefault transformer;

    @Before
    public void Init() {
        transformer = new ModelToEntityTransformerDefault();
    }

    @Test
    public void transformRelease() {
        com.andrewd.recordlabel.supermodel.ReleaseSlim model = new com.andrewd.recordlabel.supermodel.ReleaseSlim();

        model.id = 1;
        model.date = 1973;
        model.catalogueNumber = "KC3000";
        model.length = 43;
        model.printStatus = PrintStatus.InPrint;
        model.title = "Raw Power";

        Release entity = transformer.getRelease(model);

        verifyRelease(model, entity);
    }

    private void verifyRelease(com.andrewd.recordlabel.supermodel.ReleaseSlim model, Release entity) {
        Assert.assertEquals(model.id, entity.id);
        Assert.assertEquals(model.date, entity.date);
        Assert.assertEquals(model.catalogueNumber, entity.catalogueNumber);
        Assert.assertEquals(model.length, entity.length);
        Assert.assertEquals(model.printStatus, entity.printStatus);
        Assert.assertEquals(model.title, entity.title);
    }

    @Test
    public void transformRelease_MakeSureArtistIsTransformedToo() {
        com.andrewd.recordlabel.supermodel.ReleaseSlim model = new com.andrewd.recordlabel.supermodel.ReleaseSlim();
        model.artistId = 5;

        Release entity = transformer.getRelease(model);

        Assert.assertNotNull("Must transform release artist too", entity.artist);
        Assert.assertEquals(model.artistId, entity.artist.id);
    }

    @Test
    public void transformRelease_MakeSureMetadataIsTransformedToo() {
        int metadataId = 7;
        com.andrewd.recordlabel.supermodel.ReleaseSlim model = new com.andrewd.recordlabel.supermodel.ReleaseSlim();
        model.metadataIds.add(metadataId);

        Release entity = transformer.getRelease(model);

        Assert.assertNotNull("Must transform release metadata too", entity.metadata);
        Assert.assertEquals("Must transform release metadata and add it to the list", 1, entity.metadata.size());
        Assert.assertEquals("Must transform release metadata and add it to the list", metadataId, entity.metadata.get(0).id);
    }

    @Test
    public void transformRelease_MakeSureMediaTypeIsTransformedToo() {
        int mediaId = 2017;
        com.andrewd.recordlabel.supermodel.ReleaseSlim model = new com.andrewd.recordlabel.supermodel.ReleaseSlim();
        model.mediaId = mediaId;

        Release entity = transformer.getRelease(model);

        Assert.assertNotNull("Must transform release media too", entity.media);
        Assert.assertEquals("Must transform release metadata and add it to the list", mediaId, entity.media.id);
    }

    @Test
    public void transformRelease_MakeSureTracksAreTransformedToo() {
        com.andrewd.recordlabel.supermodel.ReleaseSlim entity = new com.andrewd.recordlabel.supermodel.ReleaseSlim();
        entity.tracks.add(getTrack());

        Release model = transformer.getRelease(entity);

        Assert.assertNotNull("Must transform release tracks too", model.tracks);
    }

    @Test
    public void transformArtist() {
        com.andrewd.recordlabel.supermodel.Artist entity = getArtistEntity();

        Artist model = transformer.getArtist(entity);

        Assert.assertEquals(entity.id, model.id);
        Assert.assertEquals(entity.name, model.name);
        Assert.assertEquals(entity.text, model.text);
    }

    @Test
    public void transformArtist_MakeSureMetadataIsTransformedToo() {
        com.andrewd.recordlabel.supermodel.Artist entity = new com.andrewd.recordlabel.supermodel.Artist();
        entity.metadata.add(getMetadata());

        Artist model = transformer.getArtist(entity);

        Assert.assertNotNull("Must transform artist metadata too", model.metadata);
        Assert.assertEquals("Must transform artist metadata and add it to the list", 1, model.metadata.size());
    }

    @Test
    public void transformArtist_MakeSureReferencesAreTransformedToo() {
        com.andrewd.recordlabel.supermodel.Artist entity = new com.andrewd.recordlabel.supermodel.Artist();
        entity.references.add(getReference());

        Artist model = transformer.getArtist(entity);

        Assert.assertNotNull("Must transform artist references too", model.references);
    }

    @Test
    public void transformMetadata() {
        com.andrewd.recordlabel.supermodel.Metadata entity = getMetadata();

        Metadata model = transformer.getMetadata(entity);

        Assert.assertEquals(entity.id, model.id);
        Assert.assertEquals(entity.text, model.text);
        Assert.assertEquals(entity.type, model.type);
    }

    @Test
    public void transformReference() {
        com.andrewd.recordlabel.supermodel.Reference model = getReference();

        Reference entity = transformer.getReference(model);

        Assert.assertEquals(model.id, entity.id);
        Assert.assertEquals(model.target, entity.target);
        Assert.assertEquals(model.type, entity.type);
        Assert.assertEquals(model.order, entity.order);
    }

    @Test
    public void transformMediaType() {
        com.andrewd.recordlabel.supermodel.MediaType model = getMediaType();

        MediaType entity = transformer.getMediaType(model);

        Assert.assertEquals(model.id, entity.id);
        Assert.assertEquals(model.text, entity.text);
    }

    @Test
    public void transformTrack() {
        com.andrewd.recordlabel.supermodel.Track model = getTrack();

        Track entity = transformer.getTrack(model);

        Assert.assertEquals(model.id, entity.id);
        Assert.assertEquals(model.reference, entity.reference);
        Assert.assertEquals(model.title, entity.title);
    }

    @Test
    public void transformList() {
        List<com.andrewd.recordlabel.supermodel.MediaType> list = new ArrayList<>();
        com.andrewd.recordlabel.supermodel.MediaType model1 = getMediaType();
        list.add(model1);

        List<MediaType> result = transformer.transformList(list, transformer::getMediaType);

        Assert.assertNotNull("Result of list transformation is not supposed to be null", result);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(model1.id, result.get(0).id);
        Assert.assertEquals(model1.text, result.get(0).text);
    }

    private com.andrewd.recordlabel.supermodel.Artist getArtistEntity() {
        com.andrewd.recordlabel.supermodel.Artist model = new com.andrewd.recordlabel.supermodel.Artist();
        model.id = 1;
        model.name = "Iggy & The Stooges";
        model.text = "description";
        return model;
    }

    private com.andrewd.recordlabel.supermodel.Metadata getMetadata() {
        com.andrewd.recordlabel.supermodel.Metadata model = new com.andrewd.recordlabel.supermodel.Metadata();
        model.id = 1;
        model.text = "Rock";
        model.type = MetadataType.Genre;
        return model;
    }

    private com.andrewd.recordlabel.supermodel.Reference getReference() {
        com.andrewd.recordlabel.supermodel.Reference model = new com.andrewd.recordlabel.supermodel.Reference();
        model.id = 1;
        model.target = "https://www.youtube.com/v/BJIqnXTqg8I";
        model.type = ReferenceType.Youtube;
        model.order = 2;
        return model;
    }

    private com.andrewd.recordlabel.supermodel.Track getTrack() {
        com.andrewd.recordlabel.supermodel.Track model = new com.andrewd.recordlabel.supermodel.Track();
        model.id = 1;
        model.reference = "https://www.youtube.com/watch?v=BJIqnXTqg8I";
        model.title = "Search And Destroy";
        return model;
    }

    private com.andrewd.recordlabel.supermodel.MediaType getMediaType() {
        com.andrewd.recordlabel.supermodel.MediaType model = new com.andrewd.recordlabel.supermodel.MediaType();
        model.id = 1;
        model.text = "LP";
        return model;
    }
}