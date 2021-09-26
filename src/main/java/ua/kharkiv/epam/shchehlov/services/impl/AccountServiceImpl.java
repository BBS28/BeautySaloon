package ua.kharkiv.epam.shchehlov.services.impl;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Constant;
import ua.kharkiv.epam.shchehlov.dao.AccountDao;
import ua.kharkiv.epam.shchehlov.entity.Account;
import ua.kharkiv.epam.shchehlov.exceptions.AccountDataException;
import ua.kharkiv.epam.shchehlov.security.VerifyProvidedPassword;
import ua.kharkiv.epam.shchehlov.services.AccountService;
import ua.kharkiv.epam.shchehlov.services.ValidationService;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    private static final Logger log = Logger.getLogger(AccountServiceImpl.class);
    private ValidationService validator = new ValidationServiceImpl();
    private final AccountDao accountDao;
    private static final String INVALID_INPUT = "Invalid input";
    private static final String INCORRECT_LOGIN_OR_PASSWORD = "Incorrect login or password";

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
            log.debug(INVALID_INPUT + Constant.POINTER + validator.isLoginValid(login) + " " + validator.isPasswordValid(password));
            throw new AccountDataException(INVALID_INPUT);
        }
        Account account = getByLogin(login);
        log.debug(Constant.ACCOUNT + Constant.POINTER + account);

        if (account == null || !VerifyProvidedPassword.isPasswordCorrect(password, account)) {
            log.debug(INCORRECT_LOGIN_OR_PASSWORD + Constant.POINTER + (account == null) + " " + account.getPassword().equals(password));
            throw new AccountDataException(INCORRECT_LOGIN_OR_PASSWORD);
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
