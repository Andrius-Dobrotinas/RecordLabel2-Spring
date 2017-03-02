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

@RestController
@RequestMapping("api/images/")
public class ImagesUploadController {

    @Autowired
    MultipartFileUploadService fileUploader;

    @Autowired
    ReleaseService releaseSvc;

    @Value("${" + WebConfig.IMAGES_VIRTUAL_PATH_SETTINGS_KEY + "}")
    public String imagesVirtualPath;

    @Value("${" + WebConfig.IMAGES_PHYSICAL_PATH_SETTINGS_KEY + "}")
    public String imagesPhysicalPath;

    @RequestMapping(value = "upload/{id}", method = RequestMethod.POST)
    public ResponseEntity upload(@PathVariable int id, @RequestParam("file") MultipartFile[] files) throws FileSaveException {
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

        File directory = new File(imagesPhysicalPath);

        String[] fileNames;
        fileNames = fileUploader.uploadFiles(owner, files, directory);

        // Add images path prefix to each file name
        for (int i = 0; i < fileNames.length; i++) {
            fileNames[i] = imagesVirtualPath + fileNames[i];
        }

        return ResponseEntity.ok().body(fileNames);
    }
}