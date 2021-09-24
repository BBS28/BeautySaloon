package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
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

    /**
     * Execution method for RegistrationCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command RegistrationCommand start");
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            log.debug("Command RegistrationCommand method post start");
            try {
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                String repeatPassword = request.getParameter("repeatPassword");
                String name = request.getParameter("name");
                String surname = request.getParameter("surname");
                String email = request.getParameter("email");

                ClientService clientService = new ClientServiceImpl(new ClientDaoImpl());
                Client client = clientService.register(login, password, repeatPassword, name, surname, email);
                log.info(String.format("client %s created", client.getLogin()));
                request.setAttribute("requestTypePost", "post");
                log.debug("Command RegistrationCommand method post finished");
                return "/controller?command=logIn";

            } catch (AccountDataException ex) {
                log.warn(ex.getMessage(), ex);
                request.setAttribute("warn", ex.getMessage());
                return "/WEB-INF/jsp/registration.jsp";
            }
        } else {
            log.debug("Command RegistrationCommand method get");
            return Path.REGISTRATION_PATH;
        }
    }
}
