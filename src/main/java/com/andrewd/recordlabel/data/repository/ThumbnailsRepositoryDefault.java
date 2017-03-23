package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.entities.Thumbnail;
import com.andrewd.recordlabel.data.entity.EntitySaver;
import com.andrewd.recordlabel.data.entity.comparison.IdComparer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ThumbnailsRepositoryDefault implements ThumbnailsRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private IdComparer idComparer;

    @Override
    @Transactional
    public int save(Thumbnail thumbnail, int ownerId) {
        /* Remove owner in order to not avoid any changes to the owner
        and only save the thumbnail */
        thumbnail.owner = null;

        if (idComparer.isIdDefault(thumbnail)) {
            em.persist(thumbnail);
            // Set thumbnail owner
            em.createNativeQuery(
                    "update Thumbnails t " +
                            "set t.owner_id = :ownerId " +
                            "where t.id = :thumbId")
                    .setParameter("thumbId", thumbnail.id)
                    .setParameter("ownerId", ownerId)
                    .executeUpdate();
        } else {
            em.createNativeQuery(
                    "update Thumbnails t " +
                            "set t.fileName = :filename " +
                            "where t.id = :thumbId")
                    .setParameter("filename", thumbnail.fileName)
                    .setParameter("thumbId", thumbnail.id)
                    .executeUpdate();
        }

        // Set the other side of thumbnail-owner relationship
        em.createNativeQuery(
                "update ContentBase o " +
                        "set o.thumbnail_id = :thumbId " +
                        "where o.id = :ownerId")
                .setParameter("thumbId", thumbnail.id)
                .setParameter("ownerId", ownerId)
                .executeUpdate();

        em.flush();

        return thumbnail.id;
    }

    @Override
    public Thumbnail get(int id) {
        return em.find(Thumbnail.class, id);
    }

    @Override
    public Thumbnail getByOwner(int ownerId) {
        List<Thumbnail> thumbs = em.createQuery("select i from Thumbnail i where i.owner.id =:ownerId", Thumbnail.class)
                .setParameter("ownerId", ownerId)
                .getResultList();
        if (thumbs.size() == 0) {
            return null;
        } else {
            return thumbs.get(0);
        }
    }
}