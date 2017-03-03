package com.andrewd.recordlabel.web.service;

import com.andrewd.recordlabel.common.*;
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

        // Run
        ReleaseViewModel result = builder.build(sourceModel);

        // Verify
        Assert.assertNotNull(result);
        Assert.assertEquals("Result must contain id from the source model", id, result.id);
        Assert.assertEquals("Result must contain catalogueNumber from the source model", catNo,
                result.catalogueNumber);
        Assert.assertEquals("Result must contain date from the source model", date, result.date);
        Assert.assertEquals("Result must contain length from the source model", length, result.length);
        Assert.assertEquals("Result must contain media from the source model", media, result.media);
        Assert.assertEquals("Result must contain printStatus from the source model", status,
                result.printStatus);
        Assert.assertEquals("Result must contain text from the source model", text, result.text);
        Assert.assertEquals("Result must contain title from the source model", title, result.title);
        Assert.assertEquals("Result must contain artist from the source model", artist, result.artist);
        Assert.assertEquals("Result must contain tracks from the source model", tracks, result.tracks);
        Assert.assertEquals("Result must contain metadata from the source model", metadata, result.metadata);
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
        Assert.assertEquals("Result's youtubeReferences field must contain the same number of youtube " +
                "references as there are in the source model", 2, result.youtubeReferences.size());
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
        Assert.assertEquals("Result's references field must contain the same number of non-youtube " +
                "references as there are in the source model", 2, result.references.size());
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

    @Test
    public void mustCopyEveryNonThumbnailImageToImagesList() {
        Image image1 = getImage("", false);
        Image image2 = getImage("", false);
        sourceModel.images.add(image1);
        sourceModel.images.add(image2);

        // Run
        ReleaseViewModel result = builder.build(sourceModel);

        Assert.assertNotNull("Must create images list", result.images);
        Assert.assertEquals("Must copy all non-thumbnail images to the list",
                2, result.images.size());
        Assert.assertTrue("Images list must contain every non-thumbnail image",
                result.images.contains(image1));
        Assert.assertTrue("Images list must contain every non-thumbnail image",
                result.images.contains(image2));
    }

    @Test
    public void mustNotCopyThumbnailImagesToImagesList() {
        Image image1 = getImage("", true);
        Image image2 = getImage("", false);
        sourceModel.images.add(image1);
        sourceModel.images.add(image2);

        // Run
        ReleaseViewModel result = builder.build(sourceModel);

        Assert.assertEquals("Must copy all non-thumbnail images to the list",
                1, result.images.size());
        Assert.assertTrue("Images list must contain non thumbnail images only",
                result.images.contains(image2));
    }


    /* This might not seem exactly right, but these instances of Image objects will
     not be used anymore anyway
      */
    @Test
    public void mustAppdendVirtualImagesPathToEachImagesFileName() {
        String name1 = "cover1.jpg";
        String name2 = "cover2.png";
        String virtualPath = "/path/";
        String expectedName1 = virtualPath + name1;
        String expectedName2 = virtualPath + name2;

        Image image1 = getImage(name1, false);
        Image image2 = getImage(name2, false);
        sourceModel.images.add(image1);
        sourceModel.images.add(image2);

        builder.imagesVirtualPath = virtualPath;

        // Run
        ReleaseViewModel result = builder.build(sourceModel);

        Assert.assertEquals(expectedName1, image1.fileName);
        Assert.assertEquals(expectedName2, image2.fileName);
    }

    private static Image getImage(String fileName, boolean isThumbnail) {
        Image image1 = new Image();
        image1.fileName = fileName;
        image1.isThumbnail = isThumbnail;
        return image1;
    }

    private static Reference getReference(int id, ReferenceType type) {
        Reference ref1 = new Reference();
        ref1.type = type;
        ref1.id = id;

        return ref1;
    }
}