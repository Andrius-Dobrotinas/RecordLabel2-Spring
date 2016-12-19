package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.common.*;
import com.andrewd.recordlabel.data.model.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

@Repository
public class ReleaseRepositoryDefault implements ReleaseRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Release save(Release entity) {
        // TODO: temporary, in development
        entity.references = null;
        entity.tracks = null;
        entity.metadata = null;

        if (entity.id == 0) {
            em.persist(entity);
        }
        else {
            em.merge(entity);
        }
        em.flush();

        return entity;
    }

    public Release getRelease(int id) {
        return em.find(Release.class, id);
    }

    // TODO: add the whole batching mechanism
    public List<Release> getAllReleases() {
        return em.createQuery("select i from Release", Release.class).getResultList();
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

    /* TODO: might want to make Artist properties lazily-loaded and then
    make a separate query that returns a barebones model from this same query
     */
    public List<Artist> getAllArtists() {
        return em.createQuery("select i from Artist i", Artist.class).getResultList();
    }
}