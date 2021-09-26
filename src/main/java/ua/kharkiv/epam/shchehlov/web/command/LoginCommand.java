package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Constant;
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
    private static final String START_COMMAND = "Command LoginCommand start";
    private static final String END_COMMAND = "Command LoginCommand finished";
    private static final String CASE_POST = "Case with Method post";
    private static final String CASE_GET = "Case with Method get";
    private static final String SHOW_LOGIN_PASSWORD = "login - %s, password - %s";
    private static final String SIGNED_IN = " signed in";
    private static final String ACCOUNT_ID = "accountID";
    private static final String INVALID_LOGIN_PASSWORD = "Invalid LOGIN or PASSWORD";

    /**
     * Execution method for LoginCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug(START_COMMAND);
        HttpSession session = request.getSession();
        session.setAttribute(Constant.ROLE, Constant.GUEST);
        log.debug(request.getMethod());

        if (Constant.POST_METHOD.equalsIgnoreCase(request.getMethod())) {
            log.debug(CASE_POST);
            try {
                String login = request.getParameter(Constant.ACCOUNT_LOGIN);
                String password = request.getParameter(Constant.ACCOUNT_PASSWORD);
                log.debug(String.format(SHOW_LOGIN_PASSWORD, login, password));
                AccountService accountService = new AccountServiceImpl(new AccountDaoImpl());
                Account account = accountService.login(login, password);
                log.debug(account.getClass().getSimpleName() + " " + account.getLogin() + SIGNED_IN);
                String role = account.getRole().toString().toLowerCase();
                session.setAttribute(Constant.ROLE, role);
                session.setAttribute(ACCOUNT_ID, account.getId());
                log.debug(Constant.ROLE + Constant.POINTER + role);
                log.debug(END_COMMAND);
                request.setAttribute(Constant.REQUEST_TYPE_POST, Constant.POST);
                return Path.COMMAND_MASTER_SERVICES;

            } catch (AccountDataException ex) {
                log.warn(ex.getMessage(), ex);
                request.setAttribute(Constant.WARN, INVALID_LOGIN_PASSWORD);
                return Path.LOGIN_PATH;
            }
        } else {
            log.debug(CASE_GET);
            log.debug(END_COMMAND);
            return Path.LOGIN_PATH;
        }
    }
}
