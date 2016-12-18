package com.andrewd.recordlabel.web.service;

import com.andrewd.recordlabel.common.*;
import com.andrewd.recordlabel.supermodel.*;
import com.andrewd.recordlabel.web.model.ReleaseViewModel;
import org.junit.*;

import java.util.*;

public class ReleaseViewModelTransformerDefaultTests {

    ReleaseViewModelTransformerDefault transformer;

    @Before
    public void Init() {
        transformer = new ReleaseViewModelTransformerDefault();
    }

    @Test
    public void ResultMustContainSameModelValuesExceptReferences() {
        Artist artist = new Artist();
        MediaType media = new MediaType();
        List<Track> tracks = new ArrayList();
        tracks.add(new Track());
        List<Metadata> metadata = new ArrayList<>();
        metadata.add(new Metadata());
        List<Reference> references = new ArrayList<>();
        references.add(new Reference());

        int id = 1;
        String title = "Fun House";
        String catNo = "EKS74004";
        String text = "Text";
        short date = 1970;
        short length = 5;
        PrintStatus status = PrintStatus.OutOfPrint;

        Release model = new Release();
        model.id = id;
        model.title = title;
        model.length = length;
        model.catalogueNumber = catNo;
        model.printStatus = status;
        model.text = text;
        model.date = date;
        model.artist = artist;
        model.media = media;
        model.tracks = tracks;
        model.metadata = metadata;
        model.references = references;

        ReleaseViewModelTransformerDefault transformer = new ReleaseViewModelTransformerDefault();

        // Run
        ReleaseViewModel result = transformer.transform(model);

        // Verify
        Assert.assertNotNull("Resulting model is supposed to contain a Release", result.release);
        Assert.assertEquals("Resulting model's release is supposed to contain all data that's in the input model", id, result.release.id);
        Assert.assertEquals("Resulting model's release is supposed to contain all data that's in the input model", catNo, result.release.catalogueNumber);
        Assert.assertEquals("Resulting model's release is supposed to contain all data that's in the input model", date, result.release.date);
        Assert.assertEquals("Resulting model's release is supposed to contain all data that's in the input model", length, result.release.length);
        Assert.assertEquals("Resulting model's release is supposed to contain all data that's in the input model", media, result.release.media);
        Assert.assertEquals("Resulting model's release is supposed to contain all data that's in the input model", status, result.release.printStatus);
        Assert.assertEquals("Resulting model's release is supposed to contain all data that's in the input model", text, result.release.text);
        Assert.assertEquals("Resulting model's release is supposed to contain all data that's in the input model", title, result.release.title);
        Assert.assertEquals("Resulting model's release is supposed to contain all data that's in the input model", artist, result.release.artist);
        Assert.assertEquals("Resulting model's release is supposed to contain all data that's in the input model", tracks, result.release.tracks);
        Assert.assertEquals("Resulting model's release is supposed to contain all data that's in the input model", metadata, result.release.metadata);
    }

    @Test
    public void ResultingModelMustContainYTReferencesFromInputModel() {
        Release model = new Release();

        Reference ref1 = new Reference();
        ref1.type = ReferenceType.Website;
        ref1.id = 1;

        Reference refYt1 = new Reference();
        refYt1.type = ReferenceType.Youtube;
        refYt1.id = 2;

        Reference refYt2 = new Reference();
        refYt2.type = ReferenceType.Youtube;
        refYt2.id = 3;

        Reference ref2 = new Reference();
        ref2.type = ReferenceType.File;
        ref2.id = 4;

        model.references.add(ref1);
        model.references.add(refYt1);
        model.references.add(refYt2);
        model.references.add(ref2);

        // Run
        ReleaseViewModel result = transformer.transform(model);

        // Verify
        Assert.assertNotNull("Must contain youtube references", result.youtubeReferences);
        Assert.assertEquals("Must contain no more items than there are youtube references", 2, result.youtubeReferences.size());
        Assert.assertTrue("Must contain a youtube reference", result.youtubeReferences.contains(refYt1));
        Assert.assertTrue("Must contain a youtube reference", result.youtubeReferences.contains(refYt2));
    }

    @Test
    public void ResultingReleaseModelsReferencesMustNotContainYTReferences() {
        Release model = new Release();

        Reference ref1 = new Reference();
        ref1.type = ReferenceType.Website;
        ref1.id = 1;

        Reference refYt1 = new Reference();
        refYt1.type = ReferenceType.Youtube;
        refYt1.id = 2;

        Reference refYt2 = new Reference();
        refYt2.type = ReferenceType.Youtube;
        refYt2.id = 3;

        Reference ref2 = new Reference();
        ref2.type = ReferenceType.File;
        ref2.id = 4;

        model.references.add(ref1);
        model.references.add(refYt1);
        model.references.add(refYt2);
        model.references.add(ref2);

        ReleaseViewModelTransformerDefault transformer = new ReleaseViewModelTransformerDefault();

        // Run
        ReleaseViewModel result = transformer.transform(model);

        Assert.assertNotNull("Non-youtube reference list must not be empty", result.release.references);
        Assert.assertEquals("Must contain no more items than there are non-youtube references", 2, result.release.references.size());
        Assert.assertTrue("Must contain a non-youtube reference", result.release.references.contains(ref1));
        Assert.assertTrue("Must contain a non-youtube reference", result.release.references.contains(ref2));
    }

    @Test
    public void YoutubeListMustBeInitializedByDefault() {
        Release model = new Release();
        ReleaseViewModel result = transformer.transform(model);

        Assert.assertNotNull("Youtube ref list must not be null even if there are no youtube refs", result.youtubeReferences);
    }

    @Test
    public void NonYTReferenceListMustBeInitializedEvenIfEmpty() {
        Release model = new Release();
        Reference ref1 = new Reference();
        ref1.type = ReferenceType.Youtube;
        model.references.add(ref1);
        ReleaseViewModel result = transformer.transform(model);

        Assert.assertNotNull("Non-youtube ref list must not be null even if there are no non-youtube refs", result.release.references);
    }
}