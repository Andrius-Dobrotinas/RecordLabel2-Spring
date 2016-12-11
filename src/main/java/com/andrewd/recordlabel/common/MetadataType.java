package com.andrewd.recordlabel.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MetadataType {
    Attribute,
    Genre;

    @JsonValue
    public int toValue() {
        return ordinal();
    }
}
