package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.model.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

@Repository
public class DefaultReleaseRepository {

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

    public List<MediaType> getMediaTypeList() {
        return null;
    }
}