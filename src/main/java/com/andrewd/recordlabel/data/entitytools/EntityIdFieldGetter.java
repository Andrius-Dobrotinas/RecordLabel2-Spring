package com.andrewd.recordlabel.data.entitytools;

import com.andrewd.recordlabel.common.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.*;

@Component
public class EntityIdFieldGetter implements EntityIdPropertyGetter {

    /**
     * Returns entity's id property field. An Id is a field annotated with Id annotation
     * @param entity an instance of a type annotated with Entity annotation.
     */
    @Override
    public <T> Field get(T entity) throws EntityIdPropertyGetterException, NoIdPropertyException {
        if (entity == null)
            throw new IllegalArgumentException("Entity cannot be null");

        Class type = entity.getClass();
        Annotation a = type.getAnnotation(Entity.class);
        if (a == null)
            throw new EntityIdPropertyGetterException(
                    String.format("Type %s is not annotated with Entity annotation", type.getName()),
                    type);

        Field[] fields = type.getFields();
        List<Field> annotated = Stream.of(fields)
                .filter(x -> x.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());

        if (annotated.size() == 0)
            throw new NoIdPropertyException(type);
        if (annotated.size() > 1)
            throw new NotSupportedException(
                    "Entity contains more than one Id field which is currently not supported");

        return annotated.get(0);
    }
}