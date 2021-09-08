package ua.kharkiv.epam.shchehlov.services.impl;

import ua.kharkiv.epam.shchehlov.dao.MasterDao;
import ua.kharkiv.epam.shchehlov.entity.Master;
import ua.kharkiv.epam.shchehlov.services.MasterService;

import java.util.List;

public class MasterServiceImpl implements MasterService {
    private final MasterDao masterDao;

    public MasterServiceImpl(MasterDao masterDao) {
        this.masterDao = masterDao;
    }

    @Override
    public List<Master> getAll() {
        return masterDao.getAll();
    }

    @Override
    public Master getById(Long id) {
        return masterDao.getById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return masterDao.deleteById(id);
    }

    @Override
    public Master insert(Master item) {
        return masterDao.insert(item);
    }

    @Override
    public boolean update(Master item) {
        return masterDao.update(item);
    }
}
