package ua.kharkiv.epam.shchehlov.services;

import java.util.List;

public interface BasicService<T> {
    List<T> getAll();

    T getById(Long id);

    boolean deleteById(Long id);

    T insert(T item);

    boolean update(T item);
}
