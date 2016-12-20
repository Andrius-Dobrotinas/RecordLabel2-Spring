package com.andrewd.recordlabel.data.entitytools;

import com.andrewd.recordlabel.common.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import java.lang.reflect.Field;

@RunWith(MockitoJUnitRunner.class)
public class EntityIdComparerTests {

    @InjectMocks
    EntityIdComparer comparer;

    @Mock
    EntityIdPropertyGetter idPropertyGetter;

    @Test(expected = NotSupportedException.class)
    public void MustThrowOnNonIntField() throws Exception {
        Field nonIntFiled = Fake.class.getField("bool");
        Fake object = new Fake();

        Mockito.when(idPropertyGetter.get(Matchers.eq(object))).thenReturn(nonIntFiled);

        comparer.isIdDefault(object);
    }

    @Test
    public void MustReturnFalse_WhenValuesDontMatch() throws Exception {
        Field intField = Fake.class.getField("integer");
        Fake object = new Fake();
        object.integer = 50;

        Mockito.when(idPropertyGetter.get(Matchers.eq(object))).thenReturn(intField);

        boolean result = comparer.isIdDefault(object);

        Assert.assertFalse("50 is not default integer value", result);
    }

    @Test
    public void MustReturnTrue_WhenValuesMatch() throws Exception {
        Field intField = Fake.class.getField("integer");
        Fake object = new Fake();
        object.integer = 0;

        Mockito.when(idPropertyGetter.get(Matchers.eq(object))).thenReturn(intField);

        boolean result = comparer.isIdDefault(object);

        Assert.assertTrue("0 is default integer value", result);
    }

    public class Fake {
        public boolean bool;
        public int integer;
    }
}