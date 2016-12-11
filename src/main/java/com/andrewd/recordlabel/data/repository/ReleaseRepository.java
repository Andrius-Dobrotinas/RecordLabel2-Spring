package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.model.Artist;
import com.andrewd.recordlabel.data.model.Release;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;

@Repository
public class ReleaseRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Release entity) {
        Artist artist = new Artist();
        artist.name = "Andrew D.";
        entity.artist = artist;
        em.persist(entity.artist);
        em.persist(entity);
        em.flush();
    }

    public Release getRelease(int id) {
        return em.find(Release.class, id);
    }
}