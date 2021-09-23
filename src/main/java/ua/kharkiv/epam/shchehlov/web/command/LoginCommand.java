package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Path;
import ua.kharkiv.epam.shchehlov.dao.impl.AccountDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Account;
import ua.kharkiv.epam.shchehlov.exceptions.AccountDataException;
import ua.kharkiv.epam.shchehlov.services.AccountService;
import ua.kharkiv.epam.shchehlov.services.impl.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand extends Command {
    private static final long serialVersionUID = 8481491669171573283L;
    private static final Logger log = Logger.getLogger(LoginCommand.class);

    /**
     * Execution method for LoginCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command LoginCommand start");
        HttpSession session = request.getSession();
        session.setAttribute("role", "guest");
        log.debug(request.getMethod());

        if ("POST".equalsIgnoreCase(request.getMethod())) {
            try {
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                log.debug(String.format("login - %s, password - %s", login, password));
                AccountService accountService = new AccountServiceImpl(new AccountDaoImpl());
                Account account = accountService.login(login, password);
                log.debug(String.format("%s %s signed in", account.getClass().getSimpleName(), account.getLogin()));
                String role = account.getRole().toString().toLowerCase();
                session.setAttribute("role", role);
                session.setAttribute("accountID", account.getId());
                log.debug(String.format("role ==> %s", role));
                log.debug("Command LoginCommand finished");
                request.setAttribute("requestTypePost", "post");
                return "/controller?command=showMasterServices";
            } catch (AccountDataException ex) {
                log.warn(ex.getMessage(), ex);
                request.setAttribute("warn", "Invalid LOGIN or PASSWORD");
                return Path.LOGIN_PATH;
            }
        } else {
            log.debug("Command LoginCommand finished");
            return Path.LOGIN_PATH;
        }
    }
}
