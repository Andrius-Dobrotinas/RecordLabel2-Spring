package com.andrewd.recordlabel.supermodel;

import java.util.*;

public abstract class ContentBaseSlim {
    public int id;
    public List<Integer> metadataIds = new ArrayList<>();
    public List<Reference> references = new ArrayList<>();
}