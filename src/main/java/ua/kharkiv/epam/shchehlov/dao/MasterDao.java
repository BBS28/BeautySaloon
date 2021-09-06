package ua.kharkiv.epam.shchehlov.dao;

import ua.kharkiv.epam.shchehlov.entity.Master;

public interface MasterDao extends BasicDao<Master> {
    Master getBySurname(String surname);

}
