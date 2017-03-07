package com.andrewd.recordlabel.data.entity.comparison;

public interface IdComparer {
    boolean isIdDefault(Object object) throws IdComparisonException;
}