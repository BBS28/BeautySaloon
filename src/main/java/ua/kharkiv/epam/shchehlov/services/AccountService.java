package ua.kharkiv.epam.shchehlov.services;

import ua.kharkiv.epam.shchehlov.entity.Account;

public interface AccountService extends BasicService<Account> {
    Account getByLogin(String login);
    Account login(String login, String password);
}
