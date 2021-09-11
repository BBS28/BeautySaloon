package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand extends Command{
    private static final long serialVersionUID = 8481491643671573283L;
    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("LogoutCommand starts");

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("role", "guest");
            session.removeAttribute("account");
            log.debug(session.getAttribute("role"));
        }

        log.debug("LogoutCommand finished");
        return "/WEB-INF/jsp/login.jsp";
    }
}
