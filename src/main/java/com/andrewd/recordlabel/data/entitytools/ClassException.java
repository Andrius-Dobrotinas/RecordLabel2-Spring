package com.andrewd.recordlabel.data.entitytools;

public abstract class ClassException extends Exception {

    private Class targetClass;
    public Class getTargetClass() {
        return targetClass;
    }

    public ClassException(String message, Class targetClass) {
        super(message);
        this.targetClass = targetClass;
    }
}