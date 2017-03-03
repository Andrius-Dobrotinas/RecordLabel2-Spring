package com.andrewd.recordlabel.data.entitytools;

import com.andrewd.recordlabel.common.NotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;

@Component
public class EntityIdComparer implements IdComparer {

    @Autowired
    private EntityIdPropertyGetter idPropertyGetter;

    /**
     * Retrieves entity id property and check whether it has a default value.
     * Currently, only integer id properties are supported
     * @param entity object whose id property to check
     */
    @Override
    public boolean isIdDefault(Object entity) throws IdComparisonException {
        if (entity == null)
            throw new IllegalArgumentException("Object cannot be null");

        try {
            Field keyField = idPropertyGetter.get(entity);
            if (keyField.getType() != int.class)
                throw new NotSupportedException("Currently, only int type Id fields are supported");

            Object value = keyField.get(entity);

            return value.equals(0);
        } catch (NotSupportedException e) {
            throw e;
        } catch (Exception e) {
            throw new IdComparisonException(entity.getClass(), e);
        }
    }
}