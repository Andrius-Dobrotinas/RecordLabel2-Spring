package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.entities.Thumbnail;
import org.springframework.stereotype.Repository;
import javax.persistence.*;

@Repository
public class ThumbnailsRepositoryDefault implements ThumbnailsRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Thumbnail save(Thumbnail thumbnail) {
        em.persist(thumbnail);
        em.flush();
        return thumbnail;
    }

    @Override
    public Thumbnail get(int id) {
        return em.find(Thumbnail.class, id);
    }

    @Override
    public Thumbnail getByOwner(int ownerId) {
        return em.createQuery("select i from Thumbnail i where i.owner.id =:ownerId", Thumbnail.class)
                .setParameter("ownerId", ownerId)
                .getSingleResult();
    }
}