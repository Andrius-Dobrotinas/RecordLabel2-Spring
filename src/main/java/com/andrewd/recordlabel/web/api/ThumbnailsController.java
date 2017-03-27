package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.data.EntityDoesNotExistException;
import com.andrewd.recordlabel.data.services.ImagesService;
import com.andrewd.recordlabel.web.components.ThumbnailCreator;
import com.andrewd.recordlabel.supermodels.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/thumbnails/")
public class ThumbnailsController {

    @Autowired
    private ImagesService imagesSvc;

    @Autowired
    private ThumbnailCreator thumbnailCreator;

    @RequestMapping(value = "create/{imageId}", method = RequestMethod.POST)
    public void create(@PathVariable int imageId) throws Exception {

        // get source image from the metadata store
        Image image = imagesSvc.get(imageId);
        if (image == null)
            throw new EntityDoesNotExistException(imageId);

        thumbnailCreator.createThumbnail(image);
    }
}