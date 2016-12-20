package com.andrewd.recordlabel.data.entitytools;

public interface IdComparer {
    boolean isIdDefault(Object object) throws IdComparisonException;
}