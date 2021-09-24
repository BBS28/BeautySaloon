package ua.kharkiv.epam.shchehlov.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Main class for the Command pattern implementation.
 *
 * @author B.Shchehlov
 */
public abstract class Command implements Serializable {
    private static final long serialVersionUID = 5745123698845526524L;

    /**
     * Execution method for command.
     *
     * @return Address to go once the command is executed.
     */
    public abstract String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
