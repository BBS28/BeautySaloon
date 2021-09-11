package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.impl.ClientDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Client;
import ua.kharkiv.epam.shchehlov.exceptions.AccountDataException;
import ua.kharkiv.epam.shchehlov.services.ClientService;
import ua.kharkiv.epam.shchehlov.services.impl.ClientServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegistrationCommand extends Command {
    private static final long serialVersionUID = 8481491669171573383L;
    private static final Logger log = Logger.getLogger(RegistrationCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if ("POST".equalsIgnoreCase(request.getMethod())) {
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
                return "/WEB-INF/jsp/login.jsp";

            } catch (AccountDataException ex) {
                log.warn(ex.getMessage(), ex);
                request.setAttribute("warn", ex.getMessage());
                return "/WEB-INF/jsp/registration.jsp";
            }
        } else {
            return "/WEB-INF/jsp/registration.jsp";
        }
    }
}
