package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.entities.*;
import com.andrewd.recordlabel.data.entity.EntitySaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

@Repository
class ImagesRepositoryDefault implements ImagesRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EntitySaver saver;

    @Override
    public List<Image> getImages(int ownerId) {
        return em.createQuery("select i from Image i where i.owner.id =:ownerId", Image.class)
                .setParameter("ownerId", ownerId)
                .getResultList();
    }

    /**
     * Saves a list of images associating them with a specific
     * object. Images with no Ids are added to the repository while
     * images with Ids update existing entities in the repository.
     * @param ownerId Id of an object to which these images are
     *                to be assigned
     * @param images images to be saved
     * @return a list of saved images
     */
    @Override
    @Transactional
    public List<Image> saveImages(int ownerId, List<Image> images) {
        ContentBase owner = em.find(ContentBase.class, ownerId);

        if (owner == null)
            throw new EntityNotFoundException();

        images.forEach(x -> x.owner = owner);

        saver.save(em, images);

        em.flush();

        return images;
    }
}