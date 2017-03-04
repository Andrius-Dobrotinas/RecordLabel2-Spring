package com.andrewd.recordlabel.data.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.andrewd.recordlabel.common.*;

@Entity
@Table(name = "content_references")
public class Reference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @NotNull
    public String target;

    public ReferenceType type;

    @Column(name = "item_order")
    public int order;
}