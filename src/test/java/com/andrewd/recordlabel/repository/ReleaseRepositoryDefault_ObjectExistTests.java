package com.andrewd.recordlabel.repository;

import com.andrewd.recordlabel.data.repository.ReleaseRepositoryDefault;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import javax.persistence.*;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ReleaseRepositoryDefault_ObjectExistTests {

    @InjectMocks
    ReleaseRepositoryDefault repository;

    @Mock
    EntityManager em;

    TypedQuery queryMock;
    int id;
    int count;

    @Before
    public void init() {
        id = 711;

        queryMock = Mockito.mock(TypedQuery.class);

        Mockito.when(em.createQuery(Matchers.anyString(), Matchers.any())).thenReturn(queryMock);
        Mockito.when(queryMock.setParameter(Matchers.anyString(), Matchers.anyInt())).thenReturn(queryMock);
    }

    @Test
    public void mustCreateQuery() {
        Mockito.when(queryMock.getSingleResult()).thenReturn(count);

        repository.objectExists(id);

        Mockito.verify(em, times(1)).createQuery(Matchers.anyString(), Matchers.eq(Integer.class));
    }

    @Test
    public void mustSetQueryParameter () {
        Mockito.when(queryMock.getSingleResult()).thenReturn(count);

        repository.objectExists(id);

        Mockito.verify(queryMock, times(1)).setParameter(Matchers.anyString(), Matchers.eq(id));
    }

    @Test
    public void mustGetQueryResult () {
        Mockito.when(queryMock.getSingleResult()).thenReturn(count);

        repository.objectExists(id);

        Mockito.verify(queryMock, times(1)).getSingleResult();
    }

    @Test
    public void mustReturnTrueWhenCountIs1 () {
        int count = 1;

        Mockito.when(queryMock.getSingleResult()).thenReturn(count);

        boolean result = repository.objectExists(id);

        Assert.assertTrue(result);
    }

    @Test
    public void mustReturnTrueWhenCountIs0 () {
        int count = 0;

        Mockito.when(queryMock.getSingleResult()).thenReturn(count);

        boolean result = repository.objectExists(id);

        Assert.assertFalse(result);
    }
}