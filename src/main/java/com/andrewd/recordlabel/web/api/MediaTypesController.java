package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.data.service.ReleaseService;
import com.andrewd.recordlabel.supermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/mediatypes/")
public class MediaTypesController {

    @Autowired
    ReleaseService svc;

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public List<MediaType> get() {
        return svc.getMediaTypeList();
    }
}