package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Constant;
import ua.kharkiv.epam.shchehlov.constant.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class for the NoCommand.
 *
 * @author B.Shchehlov
 */
public class NoCommand extends Command {
    private static final long serialVersionUID = -2785976616686657267L;
    private static final Logger log = Logger.getLogger(NoCommand.class);
    private static final String START_COMMAND = "NoCommand starts";
    private static final String END_COMMAND = "NoCommand finished";
    private static final String NO_SUCH_COMMAND = "No such command";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String SET_REQUEST_ATTRIBUTE_ERROR_MESSAGE = "errorMessage";

    /**
     * Execution method for command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug(START_COMMAND);
        String errorMessage = NO_SUCH_COMMAND;
        request.setAttribute(ERROR_MESSAGE, errorMessage);
        log.error(SET_REQUEST_ATTRIBUTE_ERROR_MESSAGE + Constant.POINTER + errorMessage);
        log.debug(END_COMMAND);
        return Path.ERROR_PATH;
    }
}
