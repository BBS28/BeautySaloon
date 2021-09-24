package ua.kharkiv.epam.shchehlov.dao;

import ua.kharkiv.epam.shchehlov.entity.Client;

public interface ClientDao extends BasicDao<Client> {
    Client getByLogin(String login);

}
