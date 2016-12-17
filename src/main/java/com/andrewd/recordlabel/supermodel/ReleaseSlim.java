package com.andrewd.recordlabel.supermodel;

import com.andrewd.recordlabel.common.PrintStatus;

import java.util.*;

public class ReleaseSlim extends ContentSlim {
    //@JsonProperty
    public int artistId;
    public int mediaId;
    public String title;
    public String text;
    public short date;
    public short length;
    public String catalogueNumber;
    public PrintStatus printStatus;
    public List<Track> tracks = new ArrayList<>();
}