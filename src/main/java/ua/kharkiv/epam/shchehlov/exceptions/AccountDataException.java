package ua.kharkiv.epam.shchehlov.exceptions;

public class AccountDataException extends RuntimeException {
    public AccountDataException() {
    }

    public AccountDataException(String message) {
        super(message);
    }
}
