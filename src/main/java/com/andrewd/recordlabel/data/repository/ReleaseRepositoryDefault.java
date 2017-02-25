package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.SortDirection;
import com.andrewd.recordlabel.data.entitytools.IdComparer;
import com.andrewd.recordlabel.data.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;

@Repository
public class ReleaseRepositoryDefault implements ReleaseRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private IdComparer idComparer;

    @Transactional
    public <T> T save(T entity) {
        saveNoFlush(entity);
        em.flush();
        return entity;
    }

    public <T> T saveNoFlush(T entity) {
        if (idComparer.isIdDefault(entity)) {
            em.persist(entity);
        } else {
            entity = em.merge(entity);
        }
        return entity;
    }

    @Transactional
    public <T> T[] save(T[] entities) {
        for (T entity : entities) {
            saveNoFlush(entity);
        }
        em.flush();
        return entities;
    }

    public Release getRelease(int id) {
        return em.find(Release.class, id);
    }

    public <T> T getObject(Class<T> type, int id) {
        return em.find(type, id);
    }

    public List<Release> getReleases(int batchNumber, int batchSize, String orderByProperty, SortDirection direction) {
        return getBatch(Release.class, batchNumber, batchSize, orderByProperty, direction);
    }

    public int getTotalReleaseCount() {
        return (int)((Long)em.createQuery("select count(i) from Release i")
                .getSingleResult()).longValue();
    }

    public List<MediaType> getMediaTypeList() {
        return em.createQuery("select i from MediaType i", MediaType.class)
                .getResultList();
    }

    public List<Metadata> getMetadataList() {
        return em.createQuery("select i from Metadata i", Metadata.class)
                .getResultList();
    }

    public boolean objectExists(int id) {
        return em.createQuery("select count(o) from ContentBase o where o.id=:id", Integer.class)
                .setParameter("id", id)
                .getSingleResult()  == 1;
    }

    /* TODO: might want to make Artist properties lazily-loaded and then
    make a separate query that returns a barebones model from this same query
     */
    public List<Artist> getAllArtists() {
        return em.createQuery("select i from Artist i", Artist.class).getResultList();
    }


    private <T> List<T> getBatch(Class<T> type,  int batchNumber, int batchSize, String orderProperty, SortDirection direction) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        CriteriaQuery<T> orderedQuery = addOrdering(type, builder, query, orderProperty, direction);
        return getBatch(orderedQuery, batchNumber, batchSize);
    }

    private <T> CriteriaQuery<T> addOrdering(Class<T> type, CriteriaBuilder builder, CriteriaQuery<T> query, String orderProperty, SortDirection direction) {
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

    private <T> List<T> getBatch(CriteriaQuery<T> query, int batchNumber, int batchSize) {
        return em.createQuery(query).setFirstResult((batchNumber - 1) * batchSize).setMaxResults(batchSize).getResultList();
    }
}