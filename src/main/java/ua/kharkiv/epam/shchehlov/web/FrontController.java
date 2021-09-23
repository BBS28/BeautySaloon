package ua.kharkiv.epam.shchehlov.web;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.web.command.Command;
import ua.kharkiv.epam.shchehlov.web.command.CommandContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * FrontController servlet.
 *
 * @author B.Shchehlov
 */
@WebServlet("/controller")
public class FrontController extends HttpServlet {
    private static final Logger log = Logger.getLogger(FrontController.class);

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // extract command name from the request
        String commandName = req.getParameter("command");

        //obtain command object by its name
        Command command = CommandContainer.get(commandName);

        // execute command and get forward address
//        String forward = "/WEB-INF/jsp/error_page.jsp";

        String forward = command.execute(req, resp);

        if(req.getAttribute("requestTypePost") != null) {
            log.debug("SendRedirect work");
            resp.sendRedirect(req.getContextPath() + forward);
        }else {
            log.debug("Forward work");
            req.getRequestDispatcher(forward).forward(req, resp);
        }
    }
}
