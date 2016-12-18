package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.common.MetadataType;
import com.andrewd.recordlabel.supermodel.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/metadata")
public class MetadataController {

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public List<Metadata> get() {
        // TODO: replace with a real call to repository
        List<Metadata> result = new ArrayList<>();
        Metadata model1 = new Metadata();
        model1.id = 1;
        model1.text = "artpop";
        model1.type = MetadataType.Genre;

        Metadata model2 = new Metadata();
        model2.id = 1;
        model2.text = "heavy metal";
        model2.type = MetadataType.Genre;

        result.add(model1);
        result.add(model2);

        return result;
    }
}
