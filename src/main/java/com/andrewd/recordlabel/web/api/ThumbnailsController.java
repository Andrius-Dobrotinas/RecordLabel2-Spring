package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.WebConfig;
import com.andrewd.recordlabel.data.EntityDoesNotExistException;
import com.andrewd.recordlabel.data.services.ImagesService;
import com.andrewd.recordlabel.image.resize.ImageResizer;
import com.andrewd.recordlabel.io.*;
import com.andrewd.recordlabel.io.RandomFileCreator;
import com.andrewd.recordlabel.supermodels.Image;
import com.andrewd.recordlabel.web.components.UriBuilder;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.util.function.Function;

@RestController
@RequestMapping(value = "api/thumbnails/")
public class ThumbnailsController {

    @Autowired
    private ImagesService imageSvc;

    @Autowired
    private Function<String, File> fileFactory;

    @Autowired
    private RandomFileCreator randomFileCreator;

    @Autowired
    private IOFunction<File, FileInputStream> fileInputStreamFactory;

    @Autowired
    private IOFunction<File, FileOutputStream> fileOutputStreamFactory;

    @Autowired
    private ImageResizer imageResizer;

    @Autowired
    private UriBuilder uriBuilder;

    @Autowired
    private FileExtensionGetter fileExtensionGetter;


    @Value("${" + WebConfig.THUMBNAILS_PHYSICAL_PATH_SETTINGS_KEY + "}")
    public String thumbsPhysicalPath;

    @Value("${" + WebConfig.THUMBNAILS_PREFIX_SETTINGS_KEY + "}")
    public String thumbFileNamePrefix;

    @Value("${" + WebConfig.THUMBNAILS_WIDTH_SETTINGS_KEY + "}")
    public int thumbWidth;


    @RequestMapping(value = "create", method = RequestMethod.POST)
    public void create(@RequestParam int objectId, @RequestParam int imageId)
            throws Exception
    {
        // get source image from the metadata store
        Image image = imageSvc.get(imageId);

        if (image == null)
            throw new EntityDoesNotExistException(imageId);

        // get actual source image
        String imagePath = uriBuilder
                .build(thumbsPhysicalPath, image.fileName);
        File imageFile = fileFactory.apply(imagePath);

        String extension = fileExtensionGetter
                .getFileExtension(image.fileName, false);

        // create thumbnail file
        File thumbsDirectory = fileFactory.apply(thumbsPhysicalPath);
        File thumbFile = randomFileCreator
                .createFile(thumbFileNamePrefix, extension, thumbsDirectory);

        // resize the image and write the result to the file
        try {
            try (FileInputStream imageStream
                         = fileInputStreamFactory.apply(imageFile);
                 FileOutputStream thumbnailStream
                         = fileOutputStreamFactory.apply(thumbFile)) {

                imageResizer.resizeImage(imageStream, thumbnailStream, extension, thumbWidth);
            }

            // save thumbnail to the metadata store
            imageSvc.saveThumbnail(objectId, thumbFile.getName());
        }
        catch (Exception e) {
            thumbFile.delete();
            throw e;
        }
    }
}