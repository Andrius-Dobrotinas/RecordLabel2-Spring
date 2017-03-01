package com.andrewd.recordlabel.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @NotNull
    public String path;

    public boolean isThumbnail;

    @ManyToOne(fetch = FetchType.EAGER)
    public ContentBase owner;
}