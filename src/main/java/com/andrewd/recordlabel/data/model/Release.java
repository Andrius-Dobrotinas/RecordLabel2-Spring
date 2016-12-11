package com.andrewd.recordlabel.data.model;

import com.andrewd.recordlabel.common.PrintStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="releases")
@PrimaryKeyJoinColumn(name="id")
public class Release extends Content {

    @NotNull
    public String title;

    public short date;
    public short length;

    @NotNull
    public String catalogueNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "release_Id")
    public Artist artist;
    //private int artistId;
    public int mediaId;

    @ManyToOne(fetch = FetchType.EAGER)
    public MediaType media;

    public PrintStatus printStatus;

    // TODO: tracks
}