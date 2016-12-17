package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.common.*;
import com.andrewd.recordlabel.data.service.DefaultReleaseService;
import com.andrewd.recordlabel.supermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/releases/")
public class ReleasesController {

    @Autowired
    private DefaultReleaseService releaseSvc;

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public Release get(@PathVariable int id) {
       /*int id = 1;
       releaseSvc.getRelease(id);*/

       return getRelease(id);
    }

    @RequestMapping(value = "getBatch", method = RequestMethod.GET)
    public BatchedResult<Release> get(@RequestParam(value = "number") int number, @RequestParam(value = "size") int size) {

        BatchedResult<Release> r = new BatchedResult<Release>();
        r.entries = new java.util.ArrayList<Release>();
        for(int i = 0; i < 3; i++) {
            r.entries.add(getRelease(number + size));
        }

        return r;
    }

    private Artist getArtist() {
        Artist a = new Artist();
        a.name = "Iggy & The Stooges";
        a.text = "Greatest band on earth!";
        return a;
    }
    private MediaType getMedia() {
        MediaType m = new MediaType();
        m.id = 1;
        m.text = "LP";
        return m;
    }

    private java.util.ArrayList<Track> getTracks() {
        Track t1 = new Track();
        t1.id = 1;
        t1.title = "Search And Destroy";
        t1.reference = "https://www.youtube.com/v/BJIqnXTqg8I";

        Track t2 = new Track();
        t2.id = 2;
        t2.title = "Gimme Danger";
        t2.reference = "https://www.youtube.com/v/JFAcOnhcpGA";

        java.util.ArrayList<Track> tracks = new java.util.ArrayList<Track>();
        tracks.add(t1);
        tracks.add(t2);

        return tracks;
    }

    private Release getRelease(int id) {
        Release r = new Release();
        r.id = id;
        r.artist = getArtist();
        r.catalogueNumber = "CK32000";
        r.date = 1973;
        r.length = 43;
        r.media = getMedia();
        r.printStatus = PrintStatus.OutOfPrint;
        r.text = "One of THE greatest records on earth!";
        r.title = "Raw Power";
        r.tracks = getTracks();

        return r;
    }
}