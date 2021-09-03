package ua.kharkiv.epam.shchehlov.services.impl;

import ua.kharkiv.epam.shchehlov.dao.ServiceDao;
import ua.kharkiv.epam.shchehlov.entity.Service;
import ua.kharkiv.epam.shchehlov.services.ServiceService;

import java.util.List;

public class ServiceServiceImpl implements ServiceService {

    private final ServiceDao serviceDao;

    public ServiceServiceImpl(ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    @Override
    public List<Service> getAll() {
        return serviceDao.getAll();
    }

    @Override
    public Service getById(Long id) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public Service insert(Service item) {
        return null;
    }

    @Override
    public boolean update(Service item) {
        return false;
    }
}
