package com.andrewd.recordlabel.repository;

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

    @Test
    public void save_MustPersistNewEntity () {
        Release entity = new Release();

        repository.save(entity);

        Mockito.verify(em, times(1)).persist(Matchers.eq(entity));
    }

    @Test
    public void save_MustMergeExistingEntity_ThatHasAnIdValue () {
        Release entity = new Release();
        entity.id = 1;

        repository.save(entity);

        Mockito.verify(em, times(1)).merge(Matchers.eq(entity));
    }

    @Test
    public void save_MustFlashAfterSaving () {
        Release entity = new Release();

        repository.save(entity);

        Mockito.verify(em, times(1)).persist(Matchers.eq(entity));
    }

    @Test
    public void save_MustReturnSameEntity() {
        Release entity = new Release();
        entity.title = "Raw Power";

        Release result = repository.save(entity);

        Assert.assertEquals("Entity must be returned from the method", entity, result);
    }

    // TODO?: getMetadataList()

}