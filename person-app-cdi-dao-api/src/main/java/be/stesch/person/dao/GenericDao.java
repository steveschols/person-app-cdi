package be.stesch.person.dao;

import be.stesch.person.model.Identifiable;

import java.io.Serializable;

/**
 * Created by u420643 on 3/1/2017.
 */
public interface GenericDao<T extends Identifiable, PK extends Serializable> {

    void persist(T entity);

    T find(PK id);

    T merge(T entity);

    void remove(T entity);

    void refresh(T entity);

    void flush();

}
