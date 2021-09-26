package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Constant;
import ua.kharkiv.epam.shchehlov.constant.Path;
import ua.kharkiv.epam.shchehlov.dao.impl.ClientDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Client;
import ua.kharkiv.epam.shchehlov.exceptions.AccountDataException;
import ua.kharkiv.epam.shchehlov.services.ClientService;
import ua.kharkiv.epam.shchehlov.services.impl.ClientServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationCommand extends Command {
    private static final long serialVersionUID = 8481491669171573383L;
    private static final Logger log = Logger.getLogger(RegistrationCommand.class);
    private static final String START_COMMAND = "Command RegistrationCommand start";
    private static final String END_COMMAND = "Command RegistrationCommand finished";
    private static final String CASE_POST = "Case with Method post";
    private static final String CASE_GET = "Case with Method get";

    /**
     * Execution method for RegistrationCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug(START_COMMAND);
        if (Constant.POST_METHOD.equalsIgnoreCase(request.getMethod())) {
            log.debug(CASE_POST);
            try {
                String login = request.getParameter(Constant.ACCOUNT_LOGIN);
                String password = request.getParameter(Constant.ACCOUNT_PASSWORD);
                String repeatPassword = request.getParameter(Constant.ACCOUNT_REPEAT_PASSWORD);
                String name = request.getParameter(Constant.ACCOUNT_NAME);
                String surname = request.getParameter(Constant.ACCOUNT_SURNAME);
                String email = request.getParameter(Constant.ACCOUNT_EMAIL);

                ClientService clientService = new ClientServiceImpl(new ClientDaoImpl());
                Client client = clientService.register(login, password, repeatPassword, name, surname, email);
                log.info(String.format("client %s created", client.getLogin()));
                request.setAttribute(Constant.REQUEST_TYPE_POST, Constant.POST);
                log.debug("Command RegistrationCommand method post finished");
                return Path.COMMAND_LOGIN;

            } catch (AccountDataException ex) {
                log.warn(ex.getMessage(), ex);
                request.setAttribute(Constant.WARN, ex.getMessage());
                return Path.REGISTRATION_PATH;
            }
        } else {
            log.debug(CASE_GET);
            log.debug(END_COMMAND);
            return Path.REGISTRATION_PATH;
        }
    }
}
