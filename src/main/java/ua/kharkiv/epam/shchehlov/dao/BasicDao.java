package ua.kharkiv.epam.shchehlov.dao;

import java.util.List;

public interface BasicDao<T> {
    List<T> getAll();

    T getById(Long id);

    boolean deleteById(Long id);

    T insert(T item);

    boolean update(T item);
}
