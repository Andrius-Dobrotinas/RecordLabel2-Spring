package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.SortDirection;
import com.andrewd.recordlabel.data.entity.IdComparer;
import com.andrewd.recordlabel.data.entities.*;
import com.andrewd.recordlabel.data.repository.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ReleaseRepositoryDefaultTests {

    @InjectMocks
    ReleaseRepositoryDefault repository;

    @Mock
    EntityManager em;

    @Mock
    IdComparer idComparer;

    @Mock
    BatchedQueryBuilder batchedQueryBuilder;

    @Test
    public void getReleasesInBatches_mustUseBatchedQueryBuilderToBuildQuery() {
        int batchNumber = 1;
        int batchSize = 5;
        String orderByProperty = "title";
        SortDirection direction = SortDirection.DESCENDING;

        TypedQuery<Release> query = Mockito.mock(TypedQuery.class);

        Mockito.when(batchedQueryBuilder.getOrderedQuery(Matchers.any(), Matchers.eq(Release.class), Matchers.anyInt(),
                Matchers.anyInt(), Matchers.anyString(), Matchers.any()))
                .thenReturn(query);

        repository.getReleases(batchNumber, batchSize, orderByProperty, direction);

        Mockito.verify(batchedQueryBuilder, times(1)).getOrderedQuery(Matchers.eq(em),
                Matchers.eq(Release.class), Matchers.eq(batchNumber), Matchers.eq(batchSize),
                Matchers.eq(orderByProperty), Matchers.eq(direction));
    }
}