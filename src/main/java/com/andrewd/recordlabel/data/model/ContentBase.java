package com.andrewd.recordlabel.data.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class ContentBase {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int id;

    @ManyToMany
    public List<Metadata> metadata = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "owner_id")
    public List<Reference> references = new ArrayList<>();
}