package com.andrewd.recordlabel.web.service;

import com.andrewd.recordlabel.common.*;
import com.andrewd.recordlabel.data.model.Image;
import com.andrewd.recordlabel.supermodel.*;
import com.andrewd.recordlabel.web.model.ReleaseViewModel;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class ReleaseViewModelTransformerDefaultTests {

    ReleaseViewModelBuilderDefault builder;

    Release sourceModel;

    @Before
    public void Init() {
        builder = new ReleaseViewModelBuilderDefault();
        sourceModel = new Release();
    }

    @Test
    public void mustCopyAllFieldValuesFromTheSourceToTheOutputExceptForReferences() {
        Artist artist = new Artist();
        MediaType media = new MediaType();
        List<Track> tracks = new ArrayList<>();
        tracks.add(new Track());
        List<Metadata> metadata = new ArrayList<>();
        metadata.add(new Metadata());
        List<Reference> references = new ArrayList<>();
        references.add(new Reference());
        List<Image> images = new ArrayList<>();
        images.add(new Image());

        int id = 1;
        String title = "Fun House";
        String catNo = "EKS74004";
        String text = "Text";
        short date = 1970;
        short length = 5;
        PrintStatus status = PrintStatus.OutOfPrint;

        sourceModel.id = id;
        sourceModel.title = title;
        sourceModel.length = length;
        sourceModel.catalogueNumber = catNo;
        sourceModel.printStatus = status;
        sourceModel.text = text;
        sourceModel.date = date;
        sourceModel.artist = artist;
        sourceModel.media = media;
        sourceModel.tracks = tracks;
        sourceModel.metadata = metadata;
        sourceModel.references = references;
        sourceModel.images = images;

        // Run
        ReleaseViewModel result = builder.build(sourceModel);

        // Verify
        Assert.assertNotNull(result);
        Assert.assertEquals("Result must contain id from the source model", id, result.id);
        Assert.assertEquals("Result must contain catalogueNumber from the source model", catNo, result.catalogueNumber);
        Assert.assertEquals("Result must contain date from the source model", date, result.date);
        Assert.assertEquals("Result must contain length from the source model", length, result.length);
        Assert.assertEquals("Result must contain media from the source model", media, result.media);
        Assert.assertEquals("Result must contain printStatus from the source model", status, result.printStatus);
        Assert.assertEquals("Result must contain text from the source model", text, result.text);
        Assert.assertEquals("Result must contain title from the source model", title, result.title);
        Assert.assertEquals("Result must contain artist from the source model", artist, result.artist);
        Assert.assertEquals("Result must contain tracks from the source model", tracks, result.tracks);
        Assert.assertEquals("Result must contain metadata from the source model", metadata, result.metadata);
        // TODO: Assert.assertEquals("Result must contain images from the source model", images, result.images);
    }

    @Test
    public void mustWorkIfSourceReferencesFieldIsEmpty() {
        sourceModel.references = null;

        // Run
        ReleaseViewModel result = builder.build(sourceModel);
    }

    @Test
    public void mustCopyYoutubeReferencesFromSourceModelToOutputModelsYoutubeReferencesField() {
        Reference ref1 = getReference(1, ReferenceType.Website);
        Reference ref2 = getReference(2, ReferenceType.File);
        Reference refYt1 = getReference(3, ReferenceType.Youtube);
        Reference refYt2 = getReference(4, ReferenceType.Youtube);

        sourceModel.references.add(ref1);
        sourceModel.references.add(refYt1);
        sourceModel.references.add(refYt2);
        sourceModel.references.add(ref2);

        // Run
        ReleaseViewModel result = builder.build(sourceModel);

        // Verify
        Assert.assertNotNull("Result's youtubeReferences field must contain a list of youtube references",
                result.youtubeReferences);
        Assert.assertEquals("Result's youtubeReferences field must contain the same number of youtube references as " +
                        "there are in the source model", 2, result.youtubeReferences.size());
        Assert.assertTrue("Result's youtubeReferences field must contain a youtube reference",
                result.youtubeReferences.contains(refYt1));
        Assert.assertTrue("Result's youtubeReferences field must contain a youtube reference",
                result.youtubeReferences.contains(refYt2));
    }

    @Test
    public void mustCopyNonYoutubeReferencesFromSourceModelToOutputModelsReferencesField() {
        Reference ref1 = getReference(1, ReferenceType.Website);
        Reference ref2 = getReference(2, ReferenceType.File);
        Reference refYt1 = getReference(3, ReferenceType.Youtube);
        Reference refYt2 = getReference(4, ReferenceType.Youtube);

        sourceModel.references.add(ref1);
        sourceModel.references.add(refYt1);
        sourceModel.references.add(refYt2);
        sourceModel.references.add(ref2);

        // Run
        ReleaseViewModel result = builder.build(sourceModel);

        Assert.assertNotNull("Result's references field must contain a list of non-youtube references",
                result.references);
        Assert.assertEquals("Result's references field must contain the same number of non-youtube references as " +
                "there are in the source model", 2, result.references.size());
        Assert.assertTrue("Result's references field must contain a non-youtube reference",
                result.references.contains(ref1));
        Assert.assertTrue("Result's references field must contain a non-youtube reference",
                result.references.contains(ref2));
    }

    @Test
    public void mustCreateAnEmptyYoutubeReferencesListIfSourceDoesNotContainAnyYoutubeReferences() {
        Reference ref1 = getReference(1, ReferenceType.Website);
        sourceModel.references.add(ref1);

        // Run
        ReleaseViewModel result = builder.build(sourceModel);

        Assert.assertNotNull("Youtube references list must not be null even if there are no " +
                        "youtube references in the source", result.youtubeReferences);
    }

    @Test
    public void mustCreateAnEmptyReferencesListIfSourceDoesNotContainAnyNonYoutubeReferences() {
        Reference refYt1 = getReference(1, ReferenceType.Youtube);
        sourceModel.references.add(refYt1);

        // Run
        ReleaseViewModel result = builder.build(sourceModel);

        Assert.assertNotNull("References list must not be null even if there are no non-youtube " +
                "references in the source at all", result.references);
    }

    private static Reference getReference(int id, ReferenceType type) {
        Reference ref1 = new Reference();
        ref1.type = type;
        ref1.id = id;

        return ref1;
    }
}