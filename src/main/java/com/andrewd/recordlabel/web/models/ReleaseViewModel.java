package com.andrewd.recordlabel.web.models;

import com.andrewd.recordlabel.common.PrintStatus;
import com.andrewd.recordlabel.supermodels.*;
import java.util.*;

public class ReleaseViewModel {
    public int id;
    public Artist artist;
    public MediaType media;
    public String title;
    public String text;
    public short date;
    public short length;
    public String catalogueNumber;
    public PrintStatus printStatus;
    public List<Track> tracks;
    public List<Image> images;
    public List<Metadata> metadata;
    public List<Reference> youtubeReferences;
    public List<Reference> references;
}