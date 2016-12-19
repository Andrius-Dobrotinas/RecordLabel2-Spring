package com.andrewd.recordlabel.service;

import com.andrewd.recordlabel.data.service.EntityToModelTransformerDefault;
import com.andrewd.recordlabel.common.*;
import com.andrewd.recordlabel.data.model.*;
import org.junit.*;

import java.util.*;

public class EntityToModelTransformerDefaultTests {

    EntityToModelTransformerDefault transformer;

    @Before
    public void Init() {
        transformer = new EntityToModelTransformerDefault();
    }

    @Test
    public void transformRelease() {
        Release entity = new Release();

        entity.id = 1;
        entity.date = 1973;
        entity.catalogueNumber = "KC3000";
        entity.length = 43;
        entity.printStatus = PrintStatus.InPrint;
        entity.title = "Raw Power";

        com.andrewd.recordlabel.supermodel.Release model = transformer.getRelease(entity);

        verifyRelease(entity, model);
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

        com.andrewd.recordlabel.supermodel.Release model = transformer.getRelease(entity);

        Assert.assertNotNull("Must transform release artist too", model.artist);
    }

    @Test
    public void transformRelease_MakeSureMetadataIsTransformedToo() {
        Release entity = new Release();
        entity.metadata.add(getMetadata());

        com.andrewd.recordlabel.supermodel.Release model = transformer.getRelease(entity);

        Assert.assertNotNull("Must transform release metadata too", model.metadata);
        Assert.assertEquals("Must transform release metadata and add it to the list", 1, model.metadata.size());
    }

    @Test
    public void transformRelease_MakeSureMediaTypeIsTransformedToo() {
        Release entity = new Release();
        entity.media = getMediaType();

        com.andrewd.recordlabel.supermodel.Release model = transformer.getRelease(entity);

        Assert.assertNotNull("Must transform release media too", model.media);
    }

    @Test
    public void transformListOfEntities() {
        List<Release> list = new ArrayList<Release>();
        Release entity1 = new Release();
        list.add(entity1);

        List<com.andrewd.recordlabel.supermodel.Release> results =
                transformer.transformList(list, transformer::getRelease);

        Assert.assertEquals(1, results.size());

        com.andrewd.recordlabel.supermodel.Release result1 = results.get(0);
        verifyRelease(entity1, result1);
    }

    @Test
    public void transformRelease_MakeSureTracksAreTransformedToo() {
        Release entity = new Release();
        entity.tracks.add(getTrack());

        com.andrewd.recordlabel.supermodel.Release model = transformer.getRelease(entity);

        Assert.assertNotNull("Must transform release tracks too", model.tracks);
    }

    @Test
    public void transformArtist() {
        Artist entity = getArtistEntity();

        com.andrewd.recordlabel.supermodel.Artist model = transformer.getArtist(entity);

        Assert.assertEquals(entity.id, model.id);
        Assert.assertEquals(entity.name, model.name);
        Assert.assertEquals(entity.text, model.text);
    }

    @Test
    public void transformArtist_MakeSureMetadataIsTransformedToo() {
        Artist entity = new Artist();
        entity.metadata.add(getMetadata());

        com.andrewd.recordlabel.supermodel.Artist model = transformer.getArtist(entity);

        Assert.assertNotNull("Must transform artist metadata too", model.metadata);
        Assert.assertEquals("Must transform artist metadata and add it to the list", 1, model.metadata.size());
    }

    @Test
    public void transformArtist_MakeSureReferencesAreTransformedToo() {
        Artist entity = new Artist();
        entity.references.add(getReference());

        com.andrewd.recordlabel.supermodel.Artist model = transformer.getArtist(entity);

        Assert.assertNotNull("Must transform artist references too", model.references);
    }

    @Test
    public void transformMetadata() {
        Metadata entity = getMetadata();

        com.andrewd.recordlabel.supermodel.Metadata model = transformer.getMetadata(entity);

        Assert.assertEquals(entity.id, model.id);
        Assert.assertEquals(entity.text, model.text);
        Assert.assertEquals(entity.type, model.type);
    }

    @Test
    public void transformReference() {
        Reference entity = getReference();

        com.andrewd.recordlabel.supermodel.Reference model = transformer.getReference(entity);

        Assert.assertEquals(entity.id, model.id);
        Assert.assertEquals(entity.target, model.target);
        Assert.assertEquals(entity.type, model.type);
        Assert.assertEquals(entity.order, model.order);
    }

    @Test
    public void transformTrack() {
        Track entity = getTrack();

        com.andrewd.recordlabel.supermodel.Track model = transformer.getTrack(entity);

        Assert.assertEquals(entity.id, model.id);
        Assert.assertEquals(entity.reference, model.reference);
        Assert.assertEquals(entity.title, model.title);
    }

    @Test
    public void transformMediaType() {
        MediaType entity = getMediaType();

        com.andrewd.recordlabel.supermodel.MediaType model = transformer.getMediaType(entity);

        Assert.assertEquals(entity.id, model.id);
        Assert.assertEquals(entity.text, model.text);
    }

    @Test
    public void transformToReleaseSlim() {
        Release entity = new Release();

        entity.id = 1;
        entity.date = 1973;
        entity.catalogueNumber = "KC3000";
        entity.length = 43;
        entity.printStatus = PrintStatus.InPrint;
        entity.title = "Raw Power";

        com.andrewd.recordlabel.supermodel.ReleaseSlim model = transformer.getReleaseSlim(entity);

        Assert.assertEquals(entity.id, model.id);
        Assert.assertEquals(entity.date, model.date);
        Assert.assertEquals(entity.catalogueNumber, model.catalogueNumber);
        Assert.assertEquals(entity.length, model.length);
        Assert.assertEquals(entity.printStatus, model.printStatus);
        Assert.assertEquals(entity.title, model.title);
    }

    @Test
    public void transformToReleaseSlim_MustExtractArtistId() {
        Release entity = new Release();
        Artist artist = getArtistEntity();
        entity.artist = artist;

        com.andrewd.recordlabel.supermodel.ReleaseSlim model = transformer.getReleaseSlim(entity);

        Assert.assertEquals("Must copy over artist id", artist.id, model.artistId);
    }

    @Test
    public void transformToReleaseSlim_MustExtractMediaId() {
        Release entity = new Release();
        MediaType media = getMediaType();
        entity.media = media;

        com.andrewd.recordlabel.supermodel.ReleaseSlim model = transformer.getReleaseSlim(entity);

        Assert.assertEquals("Must copy over artist id", media.id, model.mediaId);
    }

    @Test
    public void transformToReleaseSlim_MustExtractMetadataIds() {
        int id = 1;
        int id2 = 22;
        int id3 = 5;
        Release entity = new Release();
        Metadata meta = getMetadata();
        meta.id = id;
        Metadata meta2 = getMetadata();
        meta2.id = id2;
        Metadata meta3 = getMetadata();
        meta3.id = id3;
        entity.metadata.add(meta);
        entity.metadata.add(meta2);
        entity.metadata.add(meta3);

        com.andrewd.recordlabel.supermodel.ReleaseSlim model = transformer.getReleaseSlim(entity);

        Assert.assertEquals("Must transform the right number of metadata entries", 3, model.metadataIds.size());
        Assert.assertTrue("Must contain all metadata ids", model.metadataIds.contains(id));
        Assert.assertTrue("Must contain all metadata ids", model.metadataIds.contains(id2));
        Assert.assertTrue("Must contain all metadata ids", model.metadataIds.contains(id3));
    }

    @Test
    public void transformToReleaseSlim_MustAlsoTransformReferences() {
        Release entity = new Release();
        Reference ref = getReference();
        entity.references.add(ref);

        com.andrewd.recordlabel.supermodel.ReleaseSlim model = transformer.getReleaseSlim(entity);

        Assert.assertEquals("Must also transform references", 1, model.references.size());
        Assert.assertEquals("Must also transform references", ref.id, model.references.get(0).id);
        Assert.assertEquals("Must also transform references", ref.order, model.references.get(0).order);
        Assert.assertEquals("Must also transform references", ref.target, model.references.get(0).target);
        Assert.assertEquals("Must also transform references", ref.type, model.references.get(0).type);
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