package com.andrewd.recordlabel.data.entity;

import com.andrewd.recordlabel.data.entity.comparison.IdComparer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Saves entities with entity manager taking care of choosing
 * between persist and merge methods based on whether a new entity
 * is being saved or an existing entity is being updated, which is
 * determined using IdComparer. No flush is done.
 */
@Component
class EntitySaverDefault implements EntitySaver {

    @Autowired
    private IdComparer idComparer;

    @Override
    public <T> void save(EntityManager em, T entity) {
        if (idComparer.isIdDefault(entity)) {
            em.persist(entity);
        } else {
            em.merge(entity);
        }
    }

    @Override
    public <T> void save(EntityManager em, List<T> entities) {
        for (T entity : entities) {
            save(em, entity);
        }
    }
}