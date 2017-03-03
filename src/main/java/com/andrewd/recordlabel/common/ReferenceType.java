package com.andrewd.recordlabel.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ReferenceType {
    Website,
    File,
    Youtube,
    OtherVideo;

    @JsonValue
    public int toValue() {
        return ordinal();
    }
}