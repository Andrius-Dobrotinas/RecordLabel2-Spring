package com.andrewd.recordlabel.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.andrewd.recordlabel.common.*;

@Entity
@Table(name = "refs")
public class Reference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @ManyToOne//(mappedBy = "references")
    public ContentBase owner;

    @NotNull
    public String target;

    public ReferenceType type;

    @Column(name = "ref_order")
    public int order;
}