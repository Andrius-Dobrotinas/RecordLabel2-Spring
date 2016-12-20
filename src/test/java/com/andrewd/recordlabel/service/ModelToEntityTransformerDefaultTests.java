package com.andrewd.recordlabel.service;

import com.andrewd.recordlabel.common.*;
import com.andrewd.recordlabel.data.model.*;
import com.andrewd.recordlabel.data.model.Artist;
import com.andrewd.recordlabel.data.model.MediaType;
import com.andrewd.recordlabel.data.model.Metadata;
import com.andrewd.recordlabel.data.model.Reference;
import com.andrewd.recordlabel.data.model.Release;
import com.andrewd.recordlabel.data.model.Track;
import com.andrewd.recordlabel.data.service.ModelToEntityTransformerDefault;
import com.andrewd.recordlabel.supermodel.*;
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
    public void transformReleaseSlim() {
        com.andrewd.recordlabel.supermodel.ReleaseSlim model = new com.andrewd.recordlabel.supermodel.ReleaseSlim();

        model.id = 1;
        model.date = 1973;
        model.catalogueNumber = "KC3000";
        model.length = 43;
        model.printStatus = PrintStatus.InPrint;
        model.title = "Raw Power";

        Release entity = transformer.getRelease(model);

        EntityModelTransformationVerifiers.verifyReleaseSlim(entity, model);
    }

    @Test
    public void transformReleaseSlim_MustTransformArtist() {
        com.andrewd.recordlabel.supermodel.ReleaseSlim model = new com.andrewd.recordlabel.supermodel.ReleaseSlim();
        model.artistId = 5;

        Release entity = transformer.getRelease(model);

        Assert.assertNotNull("Must create release artist entity", entity.artist);
        Assert.assertEquals("Must copy release artist id to the resulting entity", model.artistId, entity.artist.id);
    }

    @Test
    public void transformReleaseSlim_MustTransformMetadata() {
        int metadataId = 7;
        com.andrewd.recordlabel.supermodel.ReleaseSlim model = new com.andrewd.recordlabel.supermodel.ReleaseSlim();
        model.metadataIds.add(metadataId);

        Release entity = transformer.getRelease(model);

        Assert.assertNotNull("Must create release metadata entity and add it to the list", entity.metadata);
        Assert.assertEquals("Must create release metadata entity and add it to the list", 1, entity.metadata.size());
        Assert.assertEquals("Must copy release metadata id to the resulting metadata entity", metadataId, entity.metadata.get(0).id);
    }

    @Test
    public void transformReleaseSlim_MustTransformMediaType() {
        int mediaId = 2017;
        com.andrewd.recordlabel.supermodel.ReleaseSlim model = new com.andrewd.recordlabel.supermodel.ReleaseSlim();
        model.mediaId = mediaId;

        Release entity = transformer.getRelease(model);

        Assert.assertNotNull("Must create release media entity", entity.media);
        Assert.assertEquals("Must copy media id to the MediaType entity", mediaId, entity.media.id);
    }

    @Test
    public void transformReleaseSlim_MustTransformTracks() {
        com.andrewd.recordlabel.supermodel.ReleaseSlim model = new com.andrewd.recordlabel.supermodel.ReleaseSlim();
        com.andrewd.recordlabel.supermodel.Track track = getTrack();
        model.tracks.add(track);

        Release entity = transformer.getRelease(model);

        Assert.assertNotNull("Must transform release tracks", entity.tracks);
        Assert.assertEquals("Must transform release tracks", 1, entity.tracks.size());
        EntityModelTransformationVerifiers.verifyTrack(entity.tracks.get(0), track);
    }

    @Test
    public void transformReleaseSlim_MustTransformReferences() {
        com.andrewd.recordlabel.supermodel.ReleaseSlim model = new com.andrewd.recordlabel.supermodel.ReleaseSlim();
        com.andrewd.recordlabel.supermodel.Reference ref = getReference();
        model.references.add(ref);

        Release entity = transformer.getRelease(model);

        Assert.assertNotNull("Must transform release references", entity.references);
        Assert.assertEquals("Must transform release references", 1, entity.references.size());
        EntityModelTransformationVerifiers.verifyReference(entity.references.get(0), ref);
    }

    @Test
    public void transformArtist() {
        com.andrewd.recordlabel.supermodel.Artist model = getArtistEntity();

        Artist entity = transformer.getArtist(model);

        EntityModelTransformationVerifiers.verifyArtist(entity, model);
    }

    @Test
    public void transformArtist_MustTransformMetadata() {
        com.andrewd.recordlabel.supermodel.Artist model = new com.andrewd.recordlabel.supermodel.Artist();
        com.andrewd.recordlabel.supermodel.Metadata meta = getMetadata();
        model.metadata.add(meta);

        Artist entity = transformer.getArtist(model);

        Assert.assertNotNull("Must transform artist metadata", entity.metadata);
        Assert.assertEquals("Must transform artist metadata", 1, entity.metadata.size());
        EntityModelTransformationVerifiers.verifyMetadata(entity.metadata.get(0), meta);
    }

    @Test
    public void transformArtist_MustTransformReferences() {
        com.andrewd.recordlabel.supermodel.Artist model = new com.andrewd.recordlabel.supermodel.Artist();
        com.andrewd.recordlabel.supermodel.Reference ref = getReference();
        model.references.add(ref);

        Artist entity = transformer.getArtist(model);

        Assert.assertNotNull("Must transform artist references", entity.references);
        Assert.assertEquals("Must transform artist references", 1, entity.references.size());
        EntityModelTransformationVerifiers.verifyReference(entity.references.get(0), ref);
    }

    @Test
    public void transformMetadata() {
        com.andrewd.recordlabel.supermodel.Metadata model = getMetadata();

        Metadata entity = transformer.getMetadata(model);

        EntityModelTransformationVerifiers.verifyMetadata(entity, model);
    }

    @Test
    public void transformReference() {
        com.andrewd.recordlabel.supermodel.Reference model = getReference();

        Reference entity = transformer.getReference(model);

        EntityModelTransformationVerifiers.verifyReference(entity, model);
    }

    @Test
    public void transformMediaType() {
        com.andrewd.recordlabel.supermodel.MediaType model = getMediaType();

        MediaType entity = transformer.getMediaType(model);

        EntityModelTransformationVerifiers.verifyMediaType(entity, model);
    }

    @Test
    public void transformTrack() {
        com.andrewd.recordlabel.supermodel.Track model = getTrack();

        Track entity = transformer.getTrack(model);

        EntityModelTransformationVerifiers.verifyTrack(entity, model);
    }

    @Test
    public void transformList() {
        List<com.andrewd.recordlabel.supermodel.MediaType> list = new ArrayList<>();
        com.andrewd.recordlabel.supermodel.MediaType model = getMediaType();
        list.add(model);

        List<MediaType> result = transformer.transformList(list, transformer::getMediaType);

        Assert.assertNotNull("Result of list transformation is not supposed to be null", result);
        Assert.assertEquals("Result of list transformation is supposed to contain entries", 1, result.size());
        EntityModelTransformationVerifiers.verifyMediaType(result.get(0), model);
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