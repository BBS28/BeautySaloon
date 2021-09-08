package ua.kharkiv.epam.shchehlov.services.impl;

import ua.kharkiv.epam.shchehlov.dao.ClientDao;
import ua.kharkiv.epam.shchehlov.entity.Client;
import ua.kharkiv.epam.shchehlov.services.ClientService;

import java.util.List;

public class ClientServiceImpl implements ClientService {
    private final ClientDao clientDao;

    public ClientServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }


    @Override
    public List<Client> getAll() {
        return clientDao.getAll();
    }

    @Override
    public Client getById(Long id) {
        return clientDao.getById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return clientDao.deleteById(id);
    }

    @Override
    public Client insert(Client item) {
        return clientDao.insert(item);
    }

    @Override
    public boolean update(Client item) {
        return clientDao.update(item);
    }
}
