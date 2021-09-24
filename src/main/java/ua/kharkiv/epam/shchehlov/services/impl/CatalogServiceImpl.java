package ua.kharkiv.epam.shchehlov.services.impl;

import ua.kharkiv.epam.shchehlov.dao.CatalogDao;
import ua.kharkiv.epam.shchehlov.entity.Catalog;
import ua.kharkiv.epam.shchehlov.services.CatalogService;

import java.util.List;

public class CatalogServiceImpl implements CatalogService {
    private final CatalogDao msDao;

    public CatalogServiceImpl(CatalogDao msDao) {
        this.msDao = msDao;
    }

    @Override
    public List<Catalog> getAll() {

        return msDao.getAll();
    }

    @Override
    public Catalog getById(Long id) {
        return msDao.getById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return msDao.deleteById(id);
    }

    @Override
    public Catalog insert(Catalog item) {
        return msDao.insert(item);
    }

    @Override
    public boolean update(Catalog item) {
        return msDao.update(item);
    }
}
