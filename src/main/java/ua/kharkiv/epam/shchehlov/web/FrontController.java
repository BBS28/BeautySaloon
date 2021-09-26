package ua.kharkiv.epam.shchehlov.web;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Constant;
import ua.kharkiv.epam.shchehlov.web.command.Command;
import ua.kharkiv.epam.shchehlov.web.command.CommandContainer;
import ua.kharkiv.epam.shchehlov.web.command.NoCommand;

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
    private static final String SEND_REDIRECT_PROCESS = "SendRedirect process";
    private static final String FORWARD_PROCESS = "Forward process";
    private static final String NO_COMMAND = "noCommand";

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
        String commandName = req.getParameter(Constant.COMMAND);
        Command command;
        try {
            //obtain command object by its name
            command = CommandContainer.get(commandName);
            log.debug(Constant.COMMAND + Constant.POINTER + command);
        }catch (Exception ex) {
            command = CommandContainer.get(NO_COMMAND);
            log.debug(Constant.COMMAND + Constant.POINTER + command);
        }

        // execute command and get forward address
        String forward = command.execute(req, resp);
        log.debug(Constant.FORWARD + Constant.POINTER + forward);

        if (req.getAttribute(Constant.REQUEST_TYPE_POST) != null) {
            log.debug(SEND_REDIRECT_PROCESS);
            resp.sendRedirect(req.getContextPath() + forward);
        } else {
            log.debug(FORWARD_PROCESS);
            req.getRequestDispatcher(forward).forward(req, resp);
        }
    }
}
