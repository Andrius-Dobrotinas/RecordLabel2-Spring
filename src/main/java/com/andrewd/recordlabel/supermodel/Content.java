package com.andrewd.recordlabel.supermodel;

import java.util.*;

public abstract class Content {
    public int id;
    public List<Metadata> metadata = new ArrayList<>();
    public List<Reference> references = new ArrayList<>();
}