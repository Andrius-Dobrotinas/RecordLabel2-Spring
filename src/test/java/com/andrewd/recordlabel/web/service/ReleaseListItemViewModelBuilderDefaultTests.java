package com.andrewd.recordlabel.web.service;

import com.andrewd.recordlabel.common.PrintStatus;
import com.andrewd.recordlabel.supermodel.*;
import com.andrewd.recordlabel.web.model.ReleaseListItemViewModel;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ReleaseListItemViewModelBuilderDefaultTests {

    @InjectMocks
    ReleaseListItemViewModelBuilderDefault builder;

    @Mock
    UrlBuilderFunction urlBuilder;

    Release release;

    final String img0Path = "path 0";
    final String imgVirtualPath = "/img/";

    @Before
    public void before() {
        builder.imagesVirtualPath = imgVirtualPath;
        release = new Release();
    }

    @Test
    public void mustReturnAnInstanceOfModel() {
        ReleaseListItemViewModel model = builder.build(release);

        Assert.assertNotNull(model);
    }

    @Test
    public void mustCopySimpleFields() {
        Release release = getRelease();

        ReleaseListItemViewModel model = builder.build(release);

        Assert.assertEquals("Must copy id", model.id, release.id);
        Assert.assertEquals("Must copy title", model.title, release.title);
        Assert.assertEquals("Must copy date", model.date, release.date);
        Assert.assertEquals("Must copy catalogueNumber", model.catalogueNumber, release.catalogueNumber);
        Assert.assertEquals("Must copy printStatus", model.printStatus, release.printStatus);
        Assert.assertEquals("Must copy media", model.media, release.media);
    }

    @Test
    public void whenSourceThumbnailIsNull_mustLeaveThumbnailUrlNull() {
        ReleaseListItemViewModel model = builder.build(release);

        Assert.assertNull(model.thumbnailUrl);
    }

    @Test
    public void mustBuildUrl() {
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.fileName = img0Path;
        release.thumbnail = thumbnail;

        ReleaseListItemViewModel model = builder.build(release);

        Mockito.verify(urlBuilder, times(1)).build(Matchers.eq(imgVirtualPath), Matchers.eq(img0Path));
    }

    @Test
    public void mustCopyArtist() {
        Release release = getRelease();
        release.artist = getArtist();

        ReleaseListItemViewModel model = builder.build(release);

        Assert.assertSame("Must copy artist", model.artist, release.artist);
    }

    @Test
    public void mustCopyMedia() {
        Release release = getRelease();
        release.artist = getArtist();

        ReleaseListItemViewModel model = builder.build(release);

        Assert.assertSame("Must copy media", model.media, release.media);
    }


    private static Release getRelease() {
        Release release = new Release();
        release.id = 1;
        release.title = "Raw Power";
        release.printStatus = PrintStatus.OutOfPrint;
        release.date = 1973;

        return release;
    }

    private static MediaType getMedia() {
        MediaType media = new MediaType();
        media.id = 5;
        media.text = "LP";

        return media;
    }

    private static Artist getArtist() {
        Artist artist = new Artist();
        artist.id = 7;
        artist.text = "Iggy And The Stooges";

        return artist;
    }
}