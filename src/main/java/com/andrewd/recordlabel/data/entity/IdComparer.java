package com.andrewd.recordlabel.data.entity;

public interface IdComparer {
    boolean isIdDefault(Object object) throws IdComparisonException;
}