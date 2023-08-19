package by.senla.training.bulyha.carservice.dao;

import java.util.List;

public interface GenericDao<T> {

    void add(T entity);

    List<T> getAll();

    void update(T object);

    T getById(Integer pk);

    T findByName(String name, String column);

    void delete(Integer id);
}