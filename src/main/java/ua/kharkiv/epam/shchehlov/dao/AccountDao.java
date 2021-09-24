package ua.kharkiv.epam.shchehlov.dao;

import ua.kharkiv.epam.shchehlov.entity.Account;

public interface AccountDao extends BasicDao<Account> {
    Account getByLogin(String login);
}
