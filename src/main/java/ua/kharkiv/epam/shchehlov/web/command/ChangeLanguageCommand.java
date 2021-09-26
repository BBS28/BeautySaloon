package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Constant;
import ua.kharkiv.epam.shchehlov.constant.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeLanguageCommand extends Command {
    private static final long serialVersionUID = 8481493066571573283L;
    private static final Logger log = Logger.getLogger(ChangeLanguageCommand.class);
    private static final String START_COMMAND = "Command ChangeLanguageCommand start";
    private static final String END_COMMAND = "Command ChangeLanguageCommand finished";

    /**
     * Execution method for ChangeLanguageCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug(START_COMMAND);
        String language = request.getParameter(Constant.LANGUAGE);
        log.debug(Constant.LANGUAGE + language);
        HttpSession session = request.getSession();
        session.setAttribute(Constant.LANGUAGE, language);
        log.debug(END_COMMAND);
        return Path.COMMAND_MASTER_SERVICES;
    }
}
