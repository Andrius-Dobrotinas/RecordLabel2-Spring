package com.andrewd.recordlabel.data.service;

public class EntityDoesNotExistException extends RuntimeException {

    private int id;
    public int getId() {
        return id;
    }

    public EntityDoesNotExistException(int id) {
        super(String.format("Entity with id %s does not exist", id));
        this.id = id;
    }
}