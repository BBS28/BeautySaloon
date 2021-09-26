package ua.kharkiv.epam.shchehlov.services.impl;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.services.ValidationService;

public class ValidationServiceImpl implements ValidationService {
    private static final String REGEX_LOGIN = "^(?=.{3,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
    private static final String REGEX_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,}$";
    private static final String REGEX_EMAIL = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$";

    private static final Logger log = Logger.getLogger(ValidationService.class);

    @Override
    public boolean isLoginValid(String login) {
        if (login == null) {
            return false;
        }
        return login.matches(REGEX_LOGIN);
    }

    @Override //Minimum four characters, at least one letter and one number:
    public boolean isPasswordValid(String password) {
        if (password == null) {
            return false;
        }
        return password.matches(REGEX_PASSWORD);
    }

    @Override
    public boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        return email.matches(REGEX_EMAIL);
    }
}