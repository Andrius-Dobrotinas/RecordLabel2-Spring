package com.andrewd.recordlabel.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PrintStatus {
    InPrint,
    OutOfPrint;

    @JsonValue
    public int toValue() {
        return ordinal();
    }
}