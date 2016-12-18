package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.supermodel.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/artists/")
public class ArtistsController {

    @RequestMapping(value = "getList", method = RequestMethod.GET)
    public List<ArtistBarebones> getList() {
        // TODO: replace with a real call to repository
        List<ArtistBarebones> result = new ArrayList<>();
        ArtistBarebones fake = new ArtistBarebones();
        fake.id = 1;
        fake.name = "Iggy";

        result.add(fake);
        return result;
    }
}