package com.andrewd.recordlabel.data.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @NotNull
    public String fileName;

    @ManyToOne(fetch = FetchType.EAGER)
    public ContentBase owner;
}