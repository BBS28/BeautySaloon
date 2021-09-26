package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Constant;
import ua.kharkiv.epam.shchehlov.constant.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand extends Command {
    private static final long serialVersionUID = 8481491643671573283L;
    private static final Logger log = Logger.getLogger(LoginCommand.class);
    private static final String START_COMMAND = "LogoutCommand starts";
    private static final String END_COMMAND = "LogoutCommand finished";
    private static final String ACCOUNT_ID = "accountID";

    /**
     * Execution method for LogoutCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug(START_COMMAND);
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute(Constant.ROLE, Constant.GUEST);
            session.removeAttribute(ACCOUNT_ID);
            log.debug(session.getAttribute(Constant.ROLE));
        }
        log.debug(END_COMMAND);
        return Path.LOGIN_PATH;
    }
}
