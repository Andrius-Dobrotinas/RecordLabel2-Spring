package com.andrewd.recordlabel.data.model;

import java.util.*;
import com.andrewd.recordlabel.common.MetadataType;

import javax.persistence.*;

@Entity
public class Metadata {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    public MetadataType type;

    public String text;

    @ManyToMany(mappedBy = "metadata")
    public List<Content> targets = new ArrayList<Content>();
}
