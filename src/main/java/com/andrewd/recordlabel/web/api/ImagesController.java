package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.WebConfig;
import com.andrewd.recordlabel.data.services.ImagesService;
import com.andrewd.recordlabel.supermodels.Image;
import com.andrewd.recordlabel.web.components.UrlBuilderFunction;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "api/images/")
public class ImagesController {

    @Autowired
    private ImagesService svc;

    @Autowired
    private UrlBuilderFunction urlBuilder;

    @Value("${" + WebConfig.IMAGES_VIRTUAL_PATH_SETTINGS_KEY + "}")
    public String imagesVirtualPath;


    @RequestMapping(value = "get/{ownerId}", method = RequestMethod.GET)
    public List<Image> get(@PathVariable int ownerId) {
        List<Image> images = svc.getImages(ownerId);

        images.forEach(image ->
                image.fileName = urlBuilder.build(imagesVirtualPath, image.fileName));

        return images;
    }
}