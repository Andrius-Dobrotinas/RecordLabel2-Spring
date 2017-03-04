package com.andrewd.recordlabel.data.entity;

import java.lang.reflect.Field;

public interface EntityIdPropertyGetter {

    /**
     * Returns entity id property field. An id is a field annotated with Id annotation
     * @param entity an instance of a type annotated with Entity annotation.
     */
    <T> Field get(T entity) throws EntityIdPropertyGetterException, NoIdPropertyException;
}