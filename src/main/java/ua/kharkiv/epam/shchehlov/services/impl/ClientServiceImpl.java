package ua.kharkiv.epam.shchehlov.services.impl;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.ClientDao;
import ua.kharkiv.epam.shchehlov.dao.impl.AccountDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Account;
import ua.kharkiv.epam.shchehlov.entity.Client;
import ua.kharkiv.epam.shchehlov.exceptions.AccountDataException;
import ua.kharkiv.epam.shchehlov.security.SecurePassword;
import ua.kharkiv.epam.shchehlov.services.AccountService;
import ua.kharkiv.epam.shchehlov.services.ClientService;
import ua.kharkiv.epam.shchehlov.services.ValidationService;

import java.util.List;

public class ClientServiceImpl implements ClientService {
    private static final Logger log = Logger.getLogger(ClientServiceImpl.class);
    private ValidationService validator = new ValidationServiceImpl();
    private final ClientDao clientDao;
    private static final String INCORRECT_LOGIN = "Incorrect login";
    private static final String INCORRECT_PASSWORD = "Incorrect password";
    private static final String DIFFERENT_PASSWORD = "Passwords are different";
    private static final String INCORRECT_EMAIL = "Incorrect email";
    private static final String LOGIN_EXISTS = "Client with this login is already exists";
    private static final String EMAIL_EXISTS = "Client with this email is already registered";
    private static final String CLIENT_AFTER_HASH = "client after hash : ";

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

    @Override
    public Client register(String login, String password, String repeatPassword, String name, String surname, String email) {
        if (!validator.isLoginValid(login)) {
            throw new AccountDataException(INCORRECT_LOGIN);
        }
        if (!validator.isPasswordValid(password) || !validator.isPasswordValid(repeatPassword)) {
            throw new AccountDataException(INCORRECT_PASSWORD);
        }
        if (!password.equals(repeatPassword)) {
            throw new AccountDataException(DIFFERENT_PASSWORD);
        }
        if (!validator.isEmailValid(email.toLowerCase())) {
            throw new AccountDataException(INCORRECT_EMAIL);
        }

        AccountService accountService = new AccountServiceImpl(new AccountDaoImpl());
        List<Account> accountList = accountService.getAll();

        for (Account account : accountList) {
            if (login.equals(account.getLogin())) {
                throw new AccountDataException(LOGIN_EXISTS);
            }
            if (email.equals(account.getEmail())) {
                throw new AccountDataException(EMAIL_EXISTS);
            }
        }

        Client client = new Client();
        client.setLogin(login);
        client.setPassword(SecurePassword.getSecurePassword(password));
        client.setName(name);
        client.setSurname(surname);
        client.setEmail(email);

        log.debug(CLIENT_AFTER_HASH +  client);
        client = insert(client);

        return client;
    }
}
