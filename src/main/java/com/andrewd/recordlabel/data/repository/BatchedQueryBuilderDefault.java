package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.SortDirection;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import javax.persistence.criteria.*;

@Component
public class BatchedQueryBuilderDefault implements BatchedQueryBuilder {

    @Override
    public <T> TypedQuery<T> getOrderedQuery(EntityManager em, Class<T> type, int batchNumber, int batchSize,
                                      String orderProperty, SortDirection direction) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        CriteriaQuery<T> orderedQuery = addOrdering(type, builder, query, orderProperty, direction);

        return getQuery(em, orderedQuery, batchNumber, batchSize);
    }

    private <T> TypedQuery<T> getQuery(EntityManager em, CriteriaQuery<T> query, int batchNumber, int batchSize) {
        return em.createQuery(query)
                .setFirstResult((batchNumber - 1) * batchSize)
                .setMaxResults(batchSize);
    }

    private <T> CriteriaQuery<T> addOrdering(Class<T> type, CriteriaBuilder builder, CriteriaQuery<T> query,
                                             String orderProperty, SortDirection direction) {
        Root<T> root = query.from(type);
        Path path = root.get(orderProperty);

        Order order;
        if (direction == SortDirection.ASCENDING) {
            order = builder.asc(path);
        } else {
            order = builder.desc(path);
        }

        return query.orderBy(order);
    }
}