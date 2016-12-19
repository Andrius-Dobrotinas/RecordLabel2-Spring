package com.andrewd.recordlabel.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.andrewd.recordlabel.common.*;

@Entity
public class Reference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @ManyToOne//(mappedBy = "references")
    public Content owner;

    public String target;
    public ReferenceType type;
    public int order;
}