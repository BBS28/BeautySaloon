package ua.kharkiv.epam.shchehlov.services.impl;

import ua.kharkiv.epam.shchehlov.dao.MasterServiceDao;
import ua.kharkiv.epam.shchehlov.entity.MasterService;
import ua.kharkiv.epam.shchehlov.services.MasterServiceService;

import java.util.List;

public class MasterServiceServiceImpl implements MasterServiceService {
    private final MasterServiceDao msDao;

    public MasterServiceServiceImpl(MasterServiceDao msDao) {
        this.msDao = msDao;
    }

    @Override
    public List<MasterService> getAll() {

        return msDao.getAll();
    }

    @Override
    public MasterService getById(Long id) {
        return msDao.getById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return msDao.deleteById(id);
    }

    @Override
    public MasterService insert(MasterService item) {
        return msDao.insert(item);
    }

    @Override
    public boolean update(MasterService item) {
        return msDao.update(item);
    }
}
