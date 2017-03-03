package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.WebConfig;
import com.andrewd.recordlabel.data.model.ContentBase;
import com.andrewd.recordlabel.data.service.ReleaseService;
import com.andrewd.recordlabel.web.io.FileSaveException;
import com.andrewd.recordlabel.web.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.Arrays;

@RestController
@RequestMapping("api/images/")
public class ImagesUploadController {

    @Autowired
    MultipartFileUploadService fileUploader;

    @Autowired
    ReleaseService releaseSvc;

    @Autowired
    public UrlBuilderFunction urlBuilder;

    @Value("${" + WebConfig.IMAGES_VIRTUAL_PATH_SETTINGS_KEY + "}")
    public String imagesVirtualPath;

    @Value("${" + WebConfig.IMAGES_PHYSICAL_PATH_SETTINGS_KEY + "}")
    public String imagesPhysicalPath;

    @RequestMapping(value = "upload/{id}", method = RequestMethod.POST)
    public ResponseEntity upload(@PathVariable int id, @RequestParam("file") MultipartFile[] files)
            throws FileSaveException {
        if (id == 0)
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("Invalid owner id"));
        if (files.length == 0)
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("No files posted"));

        // Get owner object
        ContentBase owner = releaseSvc.getObject(ContentBase.class, id);
        if (owner == null) {
            String message = String.format("Object with id %s does not exist", id);
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(message));
        }

        // Upload files
        File targetDirectory = new File(imagesPhysicalPath);
        String[] fileNames = fileUploader.uploadFiles(owner, files, targetDirectory);

        // Generate urls for each file
        String[] fileUrls = Arrays.stream(fileNames)
                .map(name -> urlBuilder.build(imagesVirtualPath, name))
                .toArray(size -> new String[size]);

        return ResponseEntity.ok().body(fileUrls);
    }
}