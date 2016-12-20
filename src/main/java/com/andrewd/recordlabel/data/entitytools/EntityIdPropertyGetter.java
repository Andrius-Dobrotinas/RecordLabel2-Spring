package com.andrewd.recordlabel.data.entitytools;

import java.lang.reflect.Field;

public interface EntityIdPropertyGetter {
    <T> Field get(T entity) throws EntityIdPropertyGetterException, NoIdPropertyException;
}