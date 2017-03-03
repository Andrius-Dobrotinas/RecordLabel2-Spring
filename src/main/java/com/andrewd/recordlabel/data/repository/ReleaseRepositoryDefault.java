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

    @Autowired
    private BatchedQueryBuilder batchedQueryBuilder;

    @Override
    @Transactional
    public <T> T save(T entity) {
        saveNoFlush(entity);
        em.flush();
        return entity;
    }

    private <T> T saveNoFlush(T entity) {
        if (idComparer.isIdDefault(entity)) {
            em.persist(entity);
        } else {
            entity = em.merge(entity);
        }
        return entity;
    }

    @Override
    @Transactional
    public <T> T[] save(T[] entities) {
        for (T entity : entities) {
            saveNoFlush(entity);
        }
        em.flush();
        return entities;
    }

    @Override
    public Release getRelease(int id) {
        return em.find(Release.class, id);
    }

    @Override
    public <T> T getObject(Class<T> type, int id) {
        return em.find(type, id);
    }

    @Override
    public List<Release> getReleases(int batchNumber, int batchSize, String orderByProperty,
                                     SortDirection direction) {
       return batchedQueryBuilder
               .getOrderedQuery(em, Release.class, batchNumber, batchSize, orderByProperty, direction)
               .getResultList();
    }

    @Override
    public int getTotalReleaseCount() {
        return (int)((Long)em.createQuery("select count(i) from Release i")
                .getSingleResult()).longValue();
    }

    @Override
    public List<MediaType> getMediaTypeList() {
        return em.createQuery("select i from MediaType i", MediaType.class)
                .getResultList();
    }

    @Override
    public List<Metadata> getMetadataList() {
        return em.createQuery("select i from Metadata i", Metadata.class)
                .getResultList();
    }

    @Override
    public boolean objectExists(int id) {
        return em.createQuery("select count(o) from ContentBase o where o.id=:id", Integer.class)
                .setParameter("id", id)
                .getSingleResult()  == 1;
    }

    /* TODO: might want to make Artist properties lazily-loaded and then
    make a separate query that returns a barebones model from this same query
     */
    @Override
    public List<Artist> getAllArtists() {
        return em.createQuery("select i from Artist i", Artist.class).getResultList();
    }
}