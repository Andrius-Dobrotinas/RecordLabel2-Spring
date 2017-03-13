package com.andrewd.recordlabel.supermodels;

import java.util.*;

public abstract class ContentBase {
    public int id;
    public List<Metadata> metadata = new ArrayList<>();
    public List<Reference> references = new ArrayList<>();
    public Thumbnail thumbnail;
}