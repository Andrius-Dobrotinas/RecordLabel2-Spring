package com.andrewd.recordlabel.repository;

import com.andrewd.recordlabel.data.entitytools.IdComparer;
import com.andrewd.recordlabel.data.model.*;
import com.andrewd.recordlabel.data.repository.ReleaseRepositoryDefault;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import javax.persistence.EntityManager;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ReleaseRepositoryDefaultTests {

    @InjectMocks
    ReleaseRepositoryDefault repository;

    @Mock
    EntityManager em;

    @Mock
    IdComparer idComparer;

    @Test
    public void save_MustCompareIds() {
        Reference entity = new Reference();

        repository.save(entity);

        Mockito.verify(idComparer, times(1)).isIdDefault(Matchers.eq(entity));
    }

    @Test
    public void save_MustPersistIfIdIsDefault() {
        Reference entity = new Reference();
        Mockito.when(idComparer.isIdDefault(Matchers.eq(entity))).thenReturn(true);

        // Run
        repository.save(entity);

        // Verify
        Mockito.verify(em, times(1)).persist(Matchers.eq(entity));
    }

    @Test
    public void save_MustMergeIfIdIsNotDefault() {
        Reference entity = new Reference();
        Mockito.when(idComparer.isIdDefault(Matchers.eq(entity))).thenReturn(false);

        // Run
        repository.save(entity);

        // Verify
        Mockito.verify(em, times(1)).merge(Matchers.eq(entity));
    }

    @Test
    public void save_MustReturnEntityIfIdIsDefault() {
        Reference entity = new Reference();
        entity.target = "target";
        Mockito.when(idComparer.isIdDefault(Matchers.eq(entity))).thenReturn(true);

        // Run
        Reference result = repository.save(entity);

        // Verify
        Assert.assertEquals(entity, result);
    }

    @Test
    public void save_MustReturnEntityIfIdIsNotDefault() {
        Reference entity = new Reference();
        entity.target = "target";

        Mockito.when(idComparer.isIdDefault(Matchers.eq(entity))).thenReturn(false);
        Mockito.when(em.merge(Matchers.eq(entity))).thenReturn(entity);

        // Run
        Reference result = repository.save(entity);

        // Verify
        Assert.assertEquals(entity, result);
    }

    @Test
    public void save_MustFlushAfterSaving () {
        Reference entity = new Reference();

        Mockito.when(idComparer.isIdDefault(Matchers.eq(entity))).thenReturn(false);

        repository.save(entity);

        Mockito.verify(em, times(1)).flush();
    }

    // TODO?: getMetadataList()
}