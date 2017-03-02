package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.SortDirection;
import javax.persistence.*;

public interface BatchedQueryBuilder {
    <T> TypedQuery<T> getOrderedQuery(EntityManager em, Class<T> type, int batchNumber, int batchSize,
                               String orderProperty, SortDirection direction);
}