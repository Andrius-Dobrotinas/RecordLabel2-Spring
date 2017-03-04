package com.andrewd.recordlabel.common;

/**
 * Thrown to indicate that requested object was not found
 * in the underlying data store
 */
public class ObjectNotFoundException extends Exception {

    private int id;

    public int getId() {
        return id;
    }

    public ObjectNotFoundException(int id) {
        super(String.format("Object with id %s not found", id));
        this.id = id;
    }
}