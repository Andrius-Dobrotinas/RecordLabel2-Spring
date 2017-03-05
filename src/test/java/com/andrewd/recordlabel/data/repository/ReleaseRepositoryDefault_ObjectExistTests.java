package com.andrewd.recordlabel.data.repository;

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

    private TypedQuery queryMock;
    private int id = 711;
    private final long count = 1;
    private final long countZero = 0;

    @Before
    public void init() {
        queryMock = Mockito.mock(TypedQuery.class);

        Mockito.when(em
                .createQuery(Matchers.anyString(), Matchers.any()))
                .thenReturn(queryMock);

        Mockito.when(queryMock
                .setParameter(Matchers.anyString(), Matchers.anyInt()))
                .thenReturn(queryMock);

        Mockito.when(queryMock
                .getSingleResult())
                .thenReturn(count);
    }

    @Test
    public void mustCreateQuery() {
        repository.objectExists(id);

        Mockito.verify(em, times(1))
                .createQuery(Matchers.anyString(), Matchers.eq(Long.class));
    }

    @Test
    public void mustSetQueryParameter () {
        repository.objectExists(id);

        Mockito.verify(queryMock, times(1))
                .setParameter(Matchers.anyString(), Matchers.eq(id));
    }

    @Test
    public void mustGetQueryResult () {
        repository.objectExists(id);

        Mockito.verify(queryMock, times(1)).getSingleResult();
    }

    @Test
    public void mustReturnTrue_whenCountIs1 () {
        boolean result = repository.objectExists(id);

        Assert.assertTrue(result);
    }

    @Test
    public void mustReturnTrue_whenCountIs0 () {
        Mockito.when(queryMock.getSingleResult())
                .thenReturn(countZero);

        boolean result = repository.objectExists(id);

        Assert.assertFalse(result);
    }
}