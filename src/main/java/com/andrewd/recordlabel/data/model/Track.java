package com.andrewd.recordlabel.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @NotNull
    public String title;

    @NotNull
    public String reference;
}
