package ua.kharkiv.epam.shchehlov.services.impl;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.AccountDao;
import ua.kharkiv.epam.shchehlov.entity.Account;
import ua.kharkiv.epam.shchehlov.exceptions.AccountDataException;
import ua.kharkiv.epam.shchehlov.security.SecurePassword;
import ua.kharkiv.epam.shchehlov.security.VerifyProvidedPassword;
import ua.kharkiv.epam.shchehlov.services.AccountService;
import ua.kharkiv.epam.shchehlov.services.ValidationService;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    private static final Logger log = Logger.getLogger(AccountServiceImpl.class);
    private ValidationService validator = new ValidationServiceImpl();
    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Account getByLogin(String login) {
        return accountDao.getByLogin(login);
    }

    @Override
    public Account login(String login, String password) {
        if (!validator.isLoginValid(login) || !validator.isPasswordValid(password)) {
            log.debug(String.format("Invalid input, %s, %s", validator.isLoginValid(login), validator.isPasswordValid(password)));
            throw new AccountDataException("Invalid input");
        }
        Account account = getByLogin(login);
        log.debug(String.format("account %s", account));

        if(account == null || !VerifyProvidedPassword.isPasswordCorrect(password, account)) {
            log.debug(String.format("Incorrect login or password, %s, %s", account == null, account.getPassword().equals(password)));
            throw new AccountDataException("Incorrect login or password");
        }
        return account;
    }

    @Override
    public List<Account> getAll() {
        return accountDao.getAll();
    }

    @Override
    public Account getById(Long id) {
        return accountDao.getById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public Account insert(Account item) {
        return null;
    }

    @Override
    public boolean update(Account item) {
        return false;
    }
}
