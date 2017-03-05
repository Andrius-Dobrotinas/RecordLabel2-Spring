package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.WebConfig;
import com.andrewd.recordlabel.data.services.*;
import com.andrewd.recordlabel.supermodels.Image;
import com.andrewd.recordlabel.web.io.FileSaveException;
import com.andrewd.recordlabel.web.models.ErrorResponse;
import com.andrewd.recordlabel.web.components.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/images/")
public class ImagesUploadController {

    @Value("${" + WebConfig.IMAGES_VIRTUAL_PATH_SETTINGS_KEY + "}")
    public String imagesVirtualPath;

    @Value("${" + WebConfig.IMAGES_PHYSICAL_PATH_SETTINGS_KEY + "}")
    public String imagesPhysicalPath;

    @Autowired
    private TransactionalMultipartFileUploader fileUploader;

    @Autowired
    private ReleaseService releaseSvc;

    @Autowired
    private ImagesService imagesSvc;

    @Autowired
    private ImageFilenameUrlifier imgUrlifier;


    @RequestMapping(value = "upload/{ownerId}", method = RequestMethod.POST)
    public ResponseEntity upload(@PathVariable int ownerId, @RequestParam("file") MultipartFile[] files)
            throws FileSaveException {
        if (ownerId == 0)
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("Invalid owner id"));
        if (files.length == 0)
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("No files posted"));

        // Check if owner object exists
        if (!releaseSvc.objectExists(ownerId)) {
            String message = String.format("Object with id %s does not exist", ownerId);
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(message));
        }

        // Upload files
        File targetDirectory = new File(imagesPhysicalPath);

        List<Image> savedImages = fileUploader.uploadFiles(files, targetDirectory,
                saveFiles -> {
                    List<Image> images = saveFiles.stream()
                            .map(x -> new Image(x.getName()))
                            .collect(Collectors.toList());

                    return imagesSvc.save(ownerId, images);
                });

        // Replace each image's filename with url
        savedImages.forEach(image -> imgUrlifier.urlify(image, imagesVirtualPath));

        return ResponseEntity.ok().body(savedImages);
    }
}