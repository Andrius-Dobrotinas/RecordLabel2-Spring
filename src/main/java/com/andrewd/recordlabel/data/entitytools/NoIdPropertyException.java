package com.andrewd.recordlabel.data.entitytools;

/**
 * Indicates that class has no key property defined
 */
public class NoIdPropertyException extends ClassException {

    public NoIdPropertyException(Class targetClass) {
        super(String.format("Class %s has no Id properties defined",
                targetClass.getName()), targetClass);
    }
}