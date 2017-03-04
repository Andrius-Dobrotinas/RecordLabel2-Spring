package com.andrewd.recordlabel.data.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="mediatypes")
public class MediaType {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int id;

    @NotNull
    public String text;
}