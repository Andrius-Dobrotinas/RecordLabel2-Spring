package com.andrewd.recordlabel.data.entity;

import com.andrewd.recordlabel.data.entities.Reference;
import com.andrewd.recordlabel.data.entity.comparison.IdComparer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class EntitySaverTests {

    @InjectMocks
    EntitySaverDefault saver;

    @Mock
    EntityManager em;

    @Mock
    IdComparer idComparer;

    private final Reference entity = new Reference();


    @Test
    public void mustCompareIds() {
        saver.save(em, entity);

        Mockito.verify(idComparer, times(1))
                .isIdDefault(Matchers.eq(entity));
    }

    @Test
    public void mustPersistIfIdIsDefault() {
        Mockito.when(idComparer.isIdDefault(Matchers.eq(entity)))
                .thenReturn(true);

        // Run
        saver.save(em, entity);

        // Verify
        Mockito.verify(em, times(1)).persist(Matchers.eq(entity));
    }

    @Test
    public void mustMergeIfIdIsNotDefault() {
        Mockito.when(idComparer.isIdDefault(Matchers.eq(entity)))
                .thenReturn(false);

        // Run
        saver.save(em, entity);

        // Verify
        Mockito.verify(em, times(1)).merge(Matchers.eq(entity));
    }

    @Test
    public void savingAnArrayOfEntities_mustPersistEachEntityWhoseIdIsDefault() {
        Reference entity2 = new Reference();
        Reference[] entities = new Reference[] { entity, entity2 };

        Mockito.when(idComparer.isIdDefault(Matchers.any(Reference.class)))
                .thenReturn(true);

        // Run
        saver.save(em, entities);

        // Verify
        Mockito.verify(em).persist(Matchers.eq(entities));
    }
}