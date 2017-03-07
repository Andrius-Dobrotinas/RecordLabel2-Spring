package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.entity.EntitySaver;
import com.andrewd.recordlabel.data.entities.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import javax.persistence.EntityManager;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ReleaseRepositoryDefault_SaveTests {

    @InjectMocks
    ReleaseRepositoryDefault repository;

    @Mock
    EntityManager em;

    @Mock
    EntitySaver saver;

    private final Reference entity = new Reference();

    @Test
    public void mustSaveEntityUsingEntitySaver() {
        repository.save(entity);

        Mockito.verify(saver, times(1))
                .save(Matchers.eq(em), Matchers.eq(entity));
    }

    @Test
    public void mustFlushAfterSaving() {
        Reference entity = new Reference();

        repository.save(entity);

        Mockito.verify(em, times(1)).flush();
    }

    @Test
    public void mustReturnEntity() {
        Reference result = repository.save(entity);

        Assert.assertEquals(entity, result);
    }

    @Test
    public void savingAnListOfEntities_mustSaveEntitiesUsingEntitySaver() {
        Reference entity2 = new Reference();
        Reference[] entities = new Reference[] { entity, entity2 };

        repository.save(entities);

        Mockito.verify(saver, times(1))
                .save(Matchers.eq(em), Matchers.eq(entities));
    }

    @Test
    public void savingAnListOfEntities_mustFlushOnceAfterSavingAll() {
        Reference entity2 = new Reference();
        Reference[] entities = new Reference[] { entity, entity2 };

        repository.save(entities);

        Mockito.verify(em, times(1)).flush();
    }
}