package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.WebConfig;
import com.andrewd.recordlabel.data.EntityDoesNotExistException;
import com.andrewd.recordlabel.data.services.*;
import com.andrewd.recordlabel.image.resize.ImageFileResizer;
import com.andrewd.recordlabel.io.*;
import com.andrewd.recordlabel.supermodels.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import java.io.*;

@RestController
@RequestMapping(value = "api/thumbnails/")
public class ThumbnailsController {

    @Autowired
    private ReleaseService releasesSvc;

    @Autowired
    private ImagesService imagesSvc;

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


    @RequestMapping(value = "create", method = RequestMethod.POST)
    public void create(@RequestParam int objectId, @RequestParam int imageId)
            throws Exception
    {
        // get source image from the metadata store
        Image image = imagesSvc.get(imageId);
        if (image == null)
            throw new EntityDoesNotExistException(imageId);

        // get target object from the data store
        ContentBase targetObject = releasesSvc.getObject(ContentBase.class, objectId);
        if (targetObject == null)
            throw new EntityDoesNotExistException(objectId);

        // get actual source image
        File imageFile = fileFactory.getFile(imagesPhysicalPath, image.fileName);

        // get image type
        String extension = fileExtensionGetter
                .getFileExtension(image.fileName, false);

        // create thumbnail file
        File thumbsDirectory = fileFactory.getFile(thumbsPhysicalPath);
        File thumbFile = randomFileCreator
                .createFile(thumbFileNamePrefix, extension, thumbsDirectory);

        // resize the image and write the result to the file
        try {
            imageFileResizer.resize(imageFile, thumbFile, extension, thumbSize);

            // save thumbnail to the metadata store
            thumbnailsSvc.save(objectId, thumbFile.getName());
        }
        catch (Exception e) {
            thumbFile.delete();
            throw e;
        }

        // delete original thumbnail if the target object had one
        if (targetObject.thumbnail != null) {
            File oldThumbFile = fileFactory
                    .getFile(thumbsPhysicalPath, targetObject.thumbnail.fileName);

            try {
                oldThumbFile.delete();
            } catch (Exception e) {
                // TODO: log it.
            }
        }

        // TODO: return saved thumbnail object?
    }
}