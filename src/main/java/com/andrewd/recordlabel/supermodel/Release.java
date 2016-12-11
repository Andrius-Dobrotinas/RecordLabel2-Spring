package com.andrewd.recordlabel.supermodel;

import java.util.List;
import com.andrewd.recordlabel.common.*;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Release extends Content {
    //@JsonProperty
    public Artist artist;
    public MediaType media;
    public String title;
    public String text;
    public short date;
    public short length;
    public String catalogueNumber;
    public PrintStatus printStatus;
    public List<Track> tracks;
}