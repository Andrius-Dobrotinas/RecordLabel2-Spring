package com.andrewd.recordlabel.web.model;

import com.andrewd.recordlabel.common.PrintStatus;
import com.andrewd.recordlabel.supermodel.*;

public class ReleaseListItemViewModel {

    public int id;
    public String thumbnailUrl;

    public Artist artist;
    public MediaType media;
    public String title;
    public short date;
    public String catalogueNumber;
    public PrintStatus printStatus;

    public ReleaseListItemViewModel(Release source, String thumbnailUrl) {
        if (source == null)
            throw new IllegalArgumentException("source is null");

        this.id = source.id;
        this.artist = source.artist;
        this.media = source.media;
        this.title = source.title;
        this.date = source.date;
        this.catalogueNumber = source.catalogueNumber;
        this.printStatus = source.printStatus;

        this.thumbnailUrl = thumbnailUrl;
    }
}