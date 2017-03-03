package com.andrewd.recordlabel.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "thumbnails")
public class Thumbnail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @NotNull
    public String fileName;

    @OneToOne(fetch = FetchType.LAZY)
    public ContentBase owner;
}