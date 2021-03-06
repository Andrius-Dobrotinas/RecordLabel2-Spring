package com.andrewd.recordlabel.data.transformers;

import com.andrewd.recordlabel.common.*;
import com.andrewd.recordlabel.data.entities.*;
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

        com.andrewd.recordlabel.supermodels.Release model = transformer.getRelease(entity);

        // Verify
        EntityModelTransformationVerifiers.verifyRelease(entity, model);
    }

    @Test
    public void transformRelease_whenEntityIsNull_mustReturnNull() {
        com.andrewd.recordlabel.supermodels.Release model = transformer.getRelease(null);

        Assert.assertNull(model);
    }

    @Test
    public void transformRelease_MustTransformArtist() {
        Release entity = new Release();
        entity.artist = getArtistEntity();

        com.andrewd.recordlabel.supermodels.Release model = transformer.getRelease(entity);

        Assert.assertNotNull("Must transform release artist", model.artist);
        EntityModelTransformationVerifiers.verifyArtist(entity.artist, model.artist);
    }

    @Test
    public void transformRelease_MustTransformMetadata() {
        Release entity = new Release();
        Metadata meta = getMetadata();
        entity.metadata.add(meta);

        com.andrewd.recordlabel.supermodels.Release model = transformer.getRelease(entity);

        Assert.assertNotNull("Must transform release metadata", model.metadata);
        Assert.assertEquals("Must transform release metadata", 1, model.metadata.size());
        EntityModelTransformationVerifiers.verifyMetadata(meta, model.metadata.get(0));
    }

    @Test
    public void transformRelease_MustTransformMediaType() {
        Release entity = new Release();
        entity.media = getMediaType();

        com.andrewd.recordlabel.supermodels.Release model = transformer.getRelease(entity);

        Assert.assertNotNull("Must transform release media", model.media);
        EntityModelTransformationVerifiers.verifyMediaType(entity.media, model.media);
    }

    @Test
    public void transformListOfEntities() {
        List<Release> list = new ArrayList<Release>();
        Release entity1 = new Release();
        list.add(entity1);

        // Run
        List<com.andrewd.recordlabel.supermodels.Release> results =
                transformer.transformList(list, transformer::getRelease);

        // Verify
        Assert.assertEquals(1, results.size());
        EntityModelTransformationVerifiers.verifyRelease(entity1, results.get(0));
    }

    @Test
    public void transformListOfEntities_whenEntityIsNull_mustReturnNull() {
        List<com.andrewd.recordlabel.supermodels.Release> results =
                transformer.transformList(null, transformer::getRelease);

        Assert.assertNull(results);
    }

    @Test
    public void transformRelease_MustTransformTracks() {
        Release entity = new Release();
        Track track = getTrack();
        entity.tracks.add(track);

        com.andrewd.recordlabel.supermodels.Release model = transformer.getRelease(entity);

        Assert.assertNotNull("Must transform release tracks", model.tracks);
        Assert.assertEquals("Must transform release tracks", 1, model.tracks.size());
        EntityModelTransformationVerifiers.verifyTrack(track, model.tracks.get(0));
    }

    @Test
    public void transformRelease_MustTransformImages() {
        Release entity = new Release();
        Image image = getImage();
        entity.images.add(image);

        com.andrewd.recordlabel.supermodels.Release model = transformer.getRelease(entity);

        Assert.assertNotNull("Must transform release images", model.images);
        Assert.assertEquals("Must transform release images", 1, model.images.size());
        EntityModelTransformationVerifiers.verifyImage(image, model.images.get(0));
    }

    @Test
    public void transformRelease_MustTransformThumbnail() {
        int releaseId = 5;
        Release entity = getRelease(releaseId);
        Thumbnail thumbnail = getThumbnail(entity);
        entity.thumbnail = thumbnail;

        com.andrewd.recordlabel.supermodels.Release model = transformer.getRelease(entity);

        Assert.assertNotNull("Must transform release thumbnail", model.thumbnail);
        EntityModelTransformationVerifiers.verifyThumbnail(thumbnail, model.thumbnail);
        Assert.assertEquals(releaseId, model.thumbnail.ownerId);
    }

    @Test
    public void transformRelease_MustTransformThumbnail_whenOwnerIsNotNull() {
        int releaseId = 5;
        Release entity = getRelease(releaseId);
        Thumbnail thumbnail = getThumbnail(entity);
        entity.thumbnail = thumbnail;
        thumbnail.owner = entity;

        com.andrewd.recordlabel.supermodels.Release model = transformer.getRelease(entity);

        Assert.assertNotNull("Must transform release thumbnail", model.thumbnail);
        EntityModelTransformationVerifiers.verifyThumbnail(thumbnail, model.thumbnail);
        Assert.assertEquals(releaseId, model.thumbnail.ownerId);
    }


    @Test
    public void transformRelease_MustTransformReferences() {
        Release entity = new Release();
        Reference ref = getReference();
        entity.references.add(ref);

        com.andrewd.recordlabel.supermodels.Release model = transformer.getRelease(entity);

        Assert.assertNotNull("Must transform release references", model.references);
        Assert.assertEquals("Must transform release references", 1, model.references.size());
        EntityModelTransformationVerifiers.verifyReference(ref, model.references.get(0));
    }

    @Test
    public void transformArtist() {
        Artist entity = getArtistEntity();

        com.andrewd.recordlabel.supermodels.Artist model = transformer.getArtist(entity);

        EntityModelTransformationVerifiers.verifyArtist(entity, model);
    }

    @Test
    public void transformArtist_whenEntityIsNull_mustReturnNull() {
        com.andrewd.recordlabel.supermodels.Artist model = transformer.getArtist(null);

        Assert.assertNull(model);
    }

    @Test
    public void transformArtist_MustTransformMetadata() {
        Artist entity = new Artist();
        Metadata meta = getMetadata();
        entity.metadata.add(meta);

        com.andrewd.recordlabel.supermodels.Artist model = transformer.getArtist(entity);

        Assert.assertNotNull("Must transform artist metadata", model.metadata);
        Assert.assertEquals("Must transform artist metadata", 1, model.metadata.size());
        EntityModelTransformationVerifiers.verifyMetadata(meta, model.metadata.get(0));
    }

    @Test
    public void transformArtist_MustTransformReferences() {
        Artist entity = new Artist();
        Reference ref = getReference();
        entity.references.add(ref);

        com.andrewd.recordlabel.supermodels.Artist model = transformer.getArtist(entity);

        // Verify
        Assert.assertNotNull("Must transform artist references", model.references);
        Assert.assertEquals("Must transform artist references", 1, model.references.size());
        EntityModelTransformationVerifiers.verifyReference(ref, model.references.get(0));
    }

    @Test
    public void transformArtistToBarebones() {
        Artist entity = getArtistEntity();

        com.andrewd.recordlabel.supermodels.ArtistBarebones model = transformer.getArtistBarebones(entity);

        Assert.assertEquals("Must copy artist id", entity.id, model.id);
        Assert.assertEquals("Must copy artist name", entity.name, model.name);
        Assert.assertEquals("Must copy artist text", entity.text, model.text);
    }

    @Test
    public void transformArtistToBarebones_whenEntityIsNull_mustReturnNull() {
        com.andrewd.recordlabel.supermodels.ArtistBarebones model = transformer.getArtistBarebones(null);

        Assert.assertNull(model);
    }

    @Test
    public void transformMetadata() {
        Metadata entity = getMetadata();

        com.andrewd.recordlabel.supermodels.Metadata model = transformer.getMetadata(entity);

        EntityModelTransformationVerifiers.verifyMetadata(entity, model);
    }

    @Test
    public void transformMetadata_whenEntityIsNull_mustReturnNull() {
        com.andrewd.recordlabel.supermodels.Metadata model = transformer.getMetadata(null);

        Assert.assertNull(model);
    }

    @Test
    public void transformReference() {
        Reference entity = getReference();

        com.andrewd.recordlabel.supermodels.Reference model = transformer.getReference(entity);

        EntityModelTransformationVerifiers.verifyReference(entity, model);
    }

    @Test
    public void transformReference_whenEntityIsNull_mustReturnNull() {
        com.andrewd.recordlabel.supermodels.Reference model = transformer.getReference(null);

        Assert.assertNull(model);
    }

    @Test
    public void transformTrack() {
        Track entity = getTrack();

        com.andrewd.recordlabel.supermodels.Track model = transformer.getTrack(entity);

        EntityModelTransformationVerifiers.verifyTrack(entity, model);
    }

    @Test
    public void transformTrack_whenEntityIsNull_mustReturnNull() {
        com.andrewd.recordlabel.supermodels.Track model = transformer.getTrack(null);

        Assert.assertNull(model);
    }

    @Test
    public void transformMediaType() {
        MediaType entity = getMediaType();

        com.andrewd.recordlabel.supermodels.MediaType model = transformer.getMediaType(entity);

        EntityModelTransformationVerifiers.verifyMediaType(entity, model);
    }

    @Test
    public void transformMediaType_whenEntityIsNull_mustReturnNull() {
        com.andrewd.recordlabel.supermodels.MediaType model = transformer.getMediaType(null);

        Assert.assertNull(model);
    }

    @Test
    public void transformImage() {
        Image entity = getImage();

        com.andrewd.recordlabel.supermodels.Image model = transformer.getImage(entity);

        EntityModelTransformationVerifiers.verifyImage(entity, model);
    }

    @Test
    public void transformImage_whenOwnerIsNotNull() {
        Image entity = getImage();
        entity.owner = getRelease(711);

        com.andrewd.recordlabel.supermodels.Image model = transformer.getImage(entity);

        EntityModelTransformationVerifiers.verifyImage(entity, model);
    }

    @Test
    public void transformImage_whenEntityIsNull_mustReturnNull() {
        com.andrewd.recordlabel.supermodels.Image model = transformer.getImage(null);

        Assert.assertNull(model);
    }

    @Test
    public void transformThumbnail() {
        Thumbnail entity = getThumbnail(null);

        com.andrewd.recordlabel.supermodels.Thumbnail model = transformer.getThumbnail(entity);

        EntityModelTransformationVerifiers.verifyThumbnail(entity, model);
    }

    @Test
    public void transformThumbnail_whenOwnerIsNotNull() {
        Release release = getRelease(711);
        Thumbnail entity = getThumbnail(release);

        com.andrewd.recordlabel.supermodels.Thumbnail model = transformer.getThumbnail(entity);

        EntityModelTransformationVerifiers.verifyThumbnail(entity, model);
    }

    @Test
    public void transformThumbnail_whenEntityIsNull_mustReturnNull() {
        com.andrewd.recordlabel.supermodels.Thumbnail model = transformer.getThumbnail(null);

        Assert.assertNull(model);
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

        com.andrewd.recordlabel.supermodels.ReleaseSlim model = transformer.getReleaseSlim(entity);

        EntityModelTransformationVerifiers.verifyReleaseSlim(entity, model);
    }

    @Test
    public void transformToReleaseSlim_whenEntityIsNull_mustReturnNull() {
        com.andrewd.recordlabel.supermodels.ReleaseSlim model = transformer.getReleaseSlim(null);

        Assert.assertNull(model);
    }

    @Test
    public void transformToReleaseSlim_MustExtractArtistId() {
        Release entity = new Release();
        Artist artist = getArtistEntity();
        entity.artist = artist;

        com.andrewd.recordlabel.supermodels.ReleaseSlim model = transformer.getReleaseSlim(entity);

        Assert.assertEquals("Must copy over artist id", artist.id, model.artistId);
    }

    @Test
    public void transformToReleaseSlim_MustExtractMediaId() {
        Release entity = new Release();
        MediaType media = getMediaType();
        entity.media = media;

        com.andrewd.recordlabel.supermodels.ReleaseSlim model = transformer.getReleaseSlim(entity);

        Assert.assertEquals("Must copy over media id", media.id, model.mediaId);
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

        com.andrewd.recordlabel.supermodels.ReleaseSlim model = transformer.getReleaseSlim(entity);

        Assert.assertEquals("Must transform the right number of metadata entries", 3, model.metadataIds.size());
        Assert.assertTrue("Must contain all metadata ids", model.metadataIds.contains(id));
        Assert.assertTrue("Must contain all metadata ids", model.metadataIds.contains(id2));
        Assert.assertTrue("Must contain all metadata ids", model.metadataIds.contains(id3));
    }

    @Test
    public void transformToReleaseSlim_MustTransformReferences() {
        Release entity = new Release();
        Reference ref = getReference();
        entity.references.add(ref);

        // Run
        com.andrewd.recordlabel.supermodels.ReleaseSlim model = transformer.getReleaseSlim(entity);

        // Verify
        Assert.assertEquals("Must transform references", 1, model.references.size());
        EntityModelTransformationVerifiers.verifyReference(ref, model.references.get(0));
    }

    @Test
    public void transformRelease_mustTransformThumbnail() {
        int releaseId = 5;
        Release entity = getRelease(releaseId);
        Thumbnail thumbnail = getThumbnail(entity);
        entity.thumbnail = thumbnail;

        com.andrewd.recordlabel.supermodels.ReleaseSlim model = transformer.getReleaseSlim(entity);

        Assert.assertNotNull("Must transform release thumbnail", model.thumbnail);
        EntityModelTransformationVerifiers.verifyThumbnail(thumbnail, model.thumbnail);
        Assert.assertEquals(releaseId, model.thumbnail.ownerId);
    }

    @Test
    public void transformRelease_mustTransformThumbnail_whenOwnerIsNotNull() {
        int releaseId = 5;
        Release entity = getRelease(releaseId);
        Thumbnail thumbnail = getThumbnail(entity);
        entity.thumbnail = thumbnail;
        thumbnail.owner = entity;

        com.andrewd.recordlabel.supermodels.ReleaseSlim model = transformer.getReleaseSlim(entity);

        Assert.assertNotNull("Must transform release thumbnail", model.thumbnail);
        EntityModelTransformationVerifiers.verifyThumbnail(thumbnail, model.thumbnail);
        Assert.assertEquals(releaseId, model.thumbnail.ownerId);
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

    private Image getImage() {
        Image entity = new Image();
        entity.id = 1;
        entity.fileName = "asd.img";
        return entity;
    }

    private Thumbnail getThumbnail(Release release) {
        Thumbnail entity = new Thumbnail();
        entity.id = 1;
        entity.fileName = "thumb.img";
        entity.owner = release;
        return entity;
    }

    private Release getRelease(int id) {
        Release entity = new Release();
        entity.id = id;
        return entity;
    }
}