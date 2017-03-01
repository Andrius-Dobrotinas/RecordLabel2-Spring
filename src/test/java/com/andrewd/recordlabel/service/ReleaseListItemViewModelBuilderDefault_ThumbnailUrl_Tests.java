package com.andrewd.recordlabel.service;

import com.andrewd.recordlabel.data.model.Image;
import com.andrewd.recordlabel.supermodel.Release;
import com.andrewd.recordlabel.web.model.ReleaseListItemViewModel;
import com.andrewd.recordlabel.web.service.ReleaseListItemViewModelBuilderDefault;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReleaseListItemViewModelBuilderDefault_ThumbnailUrl_Tests {

    @InjectMocks
    ReleaseListItemViewModelBuilderDefault builder;

    final String img1Path = "path 1";
    final String img2Path = "path 2";
    final String img0Path = "path 0";
    final String imgVirtualPath = "/img/";

    @Before
    public void before() {
        builder.imagesVirtualPath = imgVirtualPath;
    }

    @Test
    public void mustReturnAnInstanceOfModel() {
        Release release = new Release();

        ReleaseListItemViewModel model = builder.build(release);

        Assert.assertNotNull(model);
    }

    @Test
    public void mustLeaveThumbnailUrlNullIfThereAreNoImagesAtAll() {
        Release release = new Release();

        ReleaseListItemViewModel model = builder.build(release);

        Assert.assertNull(model.thumbnailUrl);
    }

    @Test
    public void mustLeaveThumbnailUrlNullIfThereIsNoThumbnailImage() {
        Release release = new Release();

        release.images.add(getImage(img1Path, false));

        ReleaseListItemViewModel model = builder.build(release);

        Assert.assertNull(model.thumbnailUrl);
    }

    @Test
    public void mustAssignThumbnailUrlFromImageThatIsThumbnail_whenThereIsOnlyOneImage() {
        Release release = new Release();

        release.images.add(getImage(img1Path, true));

        String expectedThumbnailUrl = imgVirtualPath + img1Path;

        ReleaseListItemViewModel model = builder.build(release);

        Assert.assertNotNull(model.thumbnailUrl);
        Assert.assertEquals(expectedThumbnailUrl, model.thumbnailUrl);
    }

    @Test
    public void mustAssignThumbnailUrlFromImageThatIsThumbnail_whenMoreThanOneImage() {
        Release release = new Release();

        release.images.add(getImage(img0Path, false));
        release.images.add(getImage(img1Path, true));
        release.images.add(getImage(img2Path, false));

        String expectedThumbnailUrl = imgVirtualPath + img1Path;

        ReleaseListItemViewModel model = builder.build(release);

        Assert.assertNotNull(model.thumbnailUrl);
        Assert.assertEquals(expectedThumbnailUrl, model.thumbnailUrl);
    }

    @Test
    public void mustAssignThumbnailUrlFromImageThatIsThumbnail_whenMoreThanOneImageIsThumbnail_useFirstOne() {
        Release release = new Release();


        release.images.add(getImage(img0Path, false));
        release.images.add(getImage(img1Path, true));
        release.images.add(getImage(img2Path, true));

        String expectedThumbnailUrl = imgVirtualPath + img1Path;

        ReleaseListItemViewModel model = builder.build(release);

        Assert.assertNotNull(model.thumbnailUrl);
        Assert.assertEquals(expectedThumbnailUrl, model.thumbnailUrl);
    }

    private static Image getImage(String path, boolean isThumb) {
        Image img1 = new Image();
        img1.path = path;
        img1.isThumbnail = isThumb;
        return img1;
    }
}