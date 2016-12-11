package com.andrewd.recordlabel.supermodel;

import com.andrewd.recordlabel.common.*;
import com.fasterxml.jackson.annotation.JsonValue;

public class Metadata {
    public int id;
    //@JsonValue
    public MetadataType type;
    public String text;
}