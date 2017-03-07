package com.andrewd.recordlabel.data.entity;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Interface for saving entities with entity manager that takes
 * care of choosing between persist and merge methods based on
 * whether a new entity is being saved or an existing entity is
 * being updated. No flush is to be done by implementations of
 * this interface
 */
public interface EntitySaver {

    /**
     * Saves single entity
     * @param em entity manager with which the entity is to be saved
     * @param entity entity to be saved
     * @param <T> entity type
     */
    <T> void save(EntityManager em, T entity);

    /**
     * Saves an list of entities
     * @param em entity manager with which the entities are to be saved
     * @param entities entities to be saved
     * @param <T> entity type
     */
    <T> void save(EntityManager em, List<T> entities);
}