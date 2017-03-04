package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.entities.*;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

@Repository
public class ImagesRepositoryDefault implements ImagesRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Image> getImages(int ownerId) {
        return em.createQuery("select i from Image i where i.owner.id =:ownerId")
                .setParameter("ownerId", ownerId)
                .getResultList();
    }
}