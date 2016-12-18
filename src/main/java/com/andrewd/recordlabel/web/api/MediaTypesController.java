package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.supermodel.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/mediatypes/")
public class MediaTypesController {

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public List<MediaType> get() {
        // TODO: replace with a real call to repository
        List<MediaType> result = new ArrayList<>();

        MediaType model = new MediaType();
        model.id = 1;
        model.text = "LP";

        MediaType model2 = new MediaType();
        model2.id = 2;
        model2.text = "FLAC";

        result.add(model);
        result.add(model2);

        return result;
    }
}