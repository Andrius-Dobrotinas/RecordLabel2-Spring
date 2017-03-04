package com.andrewd.recordlabel.data.entity;

public class IdComparisonException extends RuntimeException {

    private Class objectType;
    public Class getEntityType() {
        return objectType;
    }

    public IdComparisonException(Class objectType) {
        super();
        this.objectType = objectType;
    }

    public IdComparisonException(Class objectType, String message) {
        super(message);
        this.objectType = objectType;
    }

    public IdComparisonException(Class objectType, Throwable cause) {
        super(cause);
        this.objectType = objectType;
    }
}