package ua.kharkiv.epam.shchehlov.security;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.entity.Account;

/**
 *
 */
public class VerifyProvidedPassword {

    private static final Logger log = Logger.getLogger(VerifyProvidedPassword.class);

    private VerifyProvidedPassword() {
        //to hide implicit public constructor
    }

    /**
     * Method check does password encrypted or not and than check accordingly .
     *
     * @param providedPassword not encrypted password,
     *                         user
     * @return String.
     */
    public static boolean isPasswordCorrect(String providedPassword, Account account) {
        return account.getPassword().equals(SecurePassword.getSecurePassword(providedPassword));
    }

}
