package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.SortDirection;
import com.andrewd.recordlabel.data.entities.*;
import com.andrewd.recordlabel.data.entity.EntitySaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

@Repository
public class ReleaseRepositoryDefault implements ReleaseRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EntitySaver saver;

    @Autowired
    private BatchedQueryBuilder batchedQueryBuilder;

    @Override
    @Transactional
    public <T> T save(T entity) {
        saver.save(em, entity);
        em.flush();
        return entity;
    }

    @Override
    @Transactional
    public <T> T[] save(T[] entities) {
        saver.save(em, entities);
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
        return em.createQuery("select count(o) from ContentBase o where o.id=:id", Long.class)
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