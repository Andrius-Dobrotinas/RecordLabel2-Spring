package com.andrewd.recordlabel.data.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Content {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int id;

    @ManyToMany
    public List<Metadata> metadata = new ArrayList<Metadata>();

    // TODO: references
}
