package com.andrewd.recordlabel.repository;

import com.andrewd.recordlabel.data.model.*;
import com.andrewd.recordlabel.data.repository.ReleaseRepositoryDefault;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;

@RunWith(MockitoJUnitRunner.class)
public class ReleaseRepositoryDefaultTests {

    @InjectMocks
    ReleaseRepositoryDefault repository;

    @Mock
    EntityManager em;

    @Test
    public void save_MustPersistNewEntity () {
        Release entity = new Release();

        Release result = repository.save(entity);

        Mockito.verify(em, Mockito.times(1)).persist(Matchers.eq(entity));
        Mockito.verify(em, Mockito.times(1)).flush();

        Assert.assertEquals("Entity must be returned from the method", entity, result);
    }

    @Test
    public void save_MustMergeExistingEntity () {
        Release entity = new Release();
        entity.id = 1;

        Release result = repository.save(entity);

        Mockito.verify(em, Mockito.times(1)).merge(Matchers.eq(entity));
        Mockito.verify(em, Mockito.times(1)).flush();

        Assert.assertEquals("Entity must be returned from the method", entity, result);
    }
}