package com.andrewd.recordlabel.data.model;

import java.util.*;
import com.andrewd.recordlabel.common.MetadataType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Metadata {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int id;

    public MetadataType type;

    @NotNull
    public String text;

    @ManyToMany(mappedBy = "metadata")
    public List<ContentBase> targets = new ArrayList<ContentBase>();
}