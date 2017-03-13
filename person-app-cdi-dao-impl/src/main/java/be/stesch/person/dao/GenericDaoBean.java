package be.stesch.person.dao;

import be.stesch.person.model.Identifiable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * Created by u420643 on 3/1/2017.
 */
abstract class GenericDaoBean<T extends Identifiable, PK extends Serializable>
        implements GenericDao<T, PK> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> entityClass;

    GenericDaoBean(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void persist(T entity) {
        entityManager.persist(entity);
    }

    public T find(PK id) {
        return entityManager.find(entityClass, id);
    }

    public T merge(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void refresh(T entity) {
        entityManager.refresh(entity);
    }

    @Override
    public void flush() {
        entityManager.flush();
    }

}
