package ua.kharkiv.epam.shchehlov.services;

public interface ValidationService {
    boolean isLoginValid(String login);
    boolean isPasswordValid(String password);
    boolean isEmailValid(String email);
}
