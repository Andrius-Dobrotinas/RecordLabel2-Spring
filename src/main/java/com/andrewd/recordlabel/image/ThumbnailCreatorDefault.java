package com.andrewd.recordlabel.image;

import com.andrewd.recordlabel.WebConfig;
import com.andrewd.recordlabel.data.EntityDoesNotExistException;
import com.andrewd.recordlabel.data.services.ReleaseService;
import com.andrewd.recordlabel.data.services.ThumbnailsService;
import com.andrewd.recordlabel.image.resize.ImageFileResizer;
import com.andrewd.recordlabel.io.FileExtensionGetter;
import com.andrewd.recordlabel.io.FileFactory;
import com.andrewd.recordlabel.io.RandomFileCreator;
import com.andrewd.recordlabel.supermodels.Image;
import com.andrewd.recordlabel.supermodels.Thumbnail;
import com.andrewd.recordlabel.web.components.ThumbnailCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ThumbnailCreatorDefault implements ThumbnailCreator {

    @Autowired
    private ReleaseService releasesSvc;

    @Autowired
    private ThumbnailsService thumbnailsSvc;

    @Autowired
    private FileFactory fileFactory;

    @Autowired
    private RandomFileCreator randomFileCreator;

    @Autowired
    private FileExtensionGetter fileExtensionGetter;

    @Autowired
    private ImageFileResizer imageFileResizer;

    @Value("${" + WebConfig.IMAGES_PHYSICAL_PATH_SETTINGS_KEY + "}")
    public String imagesPhysicalPath;

    @Value("${" + WebConfig.THUMBNAILS_PHYSICAL_PATH_SETTINGS_KEY + "}")
    public String thumbsPhysicalPath;

    @Value("${" + WebConfig.THUMBNAILS_PREFIX_SETTINGS_KEY + "}")
    public String thumbFileNamePrefix;

    @Value("${" + WebConfig.THUMBNAILS_WIDTH_SETTINGS_KEY + "}")
    public int thumbSize;

    public void createThumbnail(Image sourceImage) throws Exception {
        if (sourceImage == null) {
            throw new IllegalArgumentException("sourceImage is null");
        }
        if (sourceImage.ownerId == 0) {
            throw new IllegalArgumentException(
                    "sourceImage's ownerId is 0 which means it is not associated with any object");
        }

        int objectId = sourceImage.ownerId;

        // check if the target object exists
        boolean targetObjExists = releasesSvc.objectExists(objectId);
        if (targetObjExists == false)
            throw new EntityDoesNotExistException(objectId);

        Thumbnail origThumb = thumbnailsSvc.getByOwner(objectId);

        // get actual source image
        File imageFile = fileFactory.getFile(imagesPhysicalPath, sourceImage.fileName);

        // get image type
        String extension = fileExtensionGetter
                .getFileExtension(sourceImage.fileName, false);

        // create thumbnail file
        File thumbsDirectory = fileFactory.getFile(thumbsPhysicalPath);
        File thumbFile = randomFileCreator
                .createFile(thumbFileNamePrefix, "." + extension, thumbsDirectory);

        // resize the image and write the result to the file
        try {
            imageFileResizer.resize(imageFile, thumbFile, extension, thumbSize);

            // save thumbnail to the metadata store
            Thumbnail thumb = new Thumbnail(thumbFile.getName(), sourceImage.ownerId);
            if (origThumb != null) {
                thumb.id = origThumb.id;
            }
            thumbnailsSvc.save(thumb);
        }
        catch (Exception e) {
            thumbFile.delete();
            throw e;
        }

        // delete original thumbnail if the target object had one
        if (origThumb != null) {
            File oldThumbFile = fileFactory
                    .getFile(thumbsPhysicalPath, origThumb.fileName);

            try {
                oldThumbFile.delete();
            } catch (Exception e) {
                // TODO: log it.
            }
        }

        // TODO: return saved thumbnail object?
    }
}