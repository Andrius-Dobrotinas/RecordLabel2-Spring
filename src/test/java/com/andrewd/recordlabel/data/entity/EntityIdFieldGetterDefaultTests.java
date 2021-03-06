package com.andrewd.recordlabel.data.entity;

import com.andrewd.recordlabel.common.*;
import org.junit.*;
import javax.persistence.*;
import java.lang.reflect.Field;

public class EntityIdFieldGetterDefaultTests {

    EntityIdFieldGetter getter;

    @Before
    public void init() {
        getter = new EntityIdFieldGetter();
    }

    @Test(expected = EntityIdPropertyGetterException.class)
    public void mustRequireTypeAnnotatedWithEntity() throws Exception {
        NonEntityType instance = new NonEntityType();
        getter.get(instance);
    }

    @Test(expected = NoIdPropertyException.class)
    public void mustThrowExceptionWhenNoIdFieldFound() throws Exception {
        EntityTypeBadNoIds instance = new EntityTypeBadNoIds();
        getter.get(instance);
    }

    @Test(expected = NotSupportedException.class)
    public void mustThrowExceptionWhenMoreThanOneIdFieldFound() throws Exception {
        EntityTypeBad instance = new EntityTypeBad();
        getter.get(instance);
    }

    @Test
    public void mustReturnFieldAnnotatedWithId() throws Exception {
        EntityType instance = new EntityType();
        Field result = getter.get(instance);

        Assert.assertNotNull("Must return a field", result);
        Assert.assertEquals("Wrong field returned", "idField", result.getName());
    }

    @Entity
    private class EntityType {
        @Id
        public int idField;
        public int irrelevant;
    }

    @Entity
    private class EntityTypeBadNoIds {
        public int irrelevant;
    }

    @Entity
    private class EntityTypeBad {
        @Id
        public int idField;
        @Id
        public int secondIdField;
    }

    private class NonEntityType {

    }
}