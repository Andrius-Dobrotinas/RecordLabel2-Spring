package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.data.service.ReleaseService;
import com.andrewd.recordlabel.supermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/artists/")
public class ArtistsController {

    @Autowired
    ReleaseService svc;

    /* TODO: this method and action name should probably be renamed
    because this thing here returns a simple list of artists,
    with no extra info about references, metadata and so on...
     */
    @RequestMapping(value = "getList", method = RequestMethod.GET)
    public List<ArtistBarebones> getList() {
        return svc.getArtistBarebonesList();
    }
}