package ua.kharkiv.epam.shchehlov.services;

import ua.kharkiv.epam.shchehlov.entity.Client;

public interface ClientService extends BasicService<Client> {
    Client register (String login,
                     String password,
                     String repeatPassword,
                     String name,
                     String surname,
                     String email);
}
