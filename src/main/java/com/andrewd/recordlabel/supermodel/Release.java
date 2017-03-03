package com.andrewd.recordlabel.supermodel;

import java.util.*;
import com.andrewd.recordlabel.common.*;

public class Release extends ContentBase {
    //@JsonProperty
    public Artist artist;
    public MediaType media;
    public String title;
    public String text;
    public short date;
    public short length;
    public String catalogueNumber;
    public PrintStatus printStatus;
    public List<Track> tracks = new ArrayList<>();
    public List<Image> images = new ArrayList<>();
    public Thumbnail thumbnail;
}