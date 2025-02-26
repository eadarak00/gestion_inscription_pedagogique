package sn.uasz.m1.inscription.dao;

import java.util.List;

public interface IDAO<T> {
    T save(T o);
    T findById(Long id);
    List<T> findAll();
    T update(Long id, T o);
    void delete(Long id);

}
