package ua.kharkiv.epam.shchehlov.dao;

import ua.kharkiv.epam.shchehlov.entity.Service;

public interface ServiceDao extends BasicDao<Service> {
    Service getByName(String name);
}
