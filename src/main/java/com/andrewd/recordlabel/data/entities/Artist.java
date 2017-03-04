package com.andrewd.recordlabel.data.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="artists")
@PrimaryKeyJoinColumn(name="id")
public class Artist extends ContentBase {

    @NotNull
    public String name;

    public String text;

    @OneToMany(mappedBy = "artist", fetch = FetchType.EAGER)
    public List<Release> releases = new ArrayList<Release>();
}
