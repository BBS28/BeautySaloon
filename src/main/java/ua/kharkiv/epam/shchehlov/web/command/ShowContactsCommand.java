package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowContactsCommand extends Command {
    private static final Logger log = Logger.getLogger(ShowContactsCommand.class);
    private static final long serialVersionUID = 848549821177573283L;

    /**
     * Execution method for ShowContactsCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command ShowContactsCommand start");
        log.debug("Command ShowContactsCommand finished");
        return Path.CONTACTS_PATH;
    }
}
