package ua.kharkiv.epam.shchehlov.web;

import ua.kharkiv.epam.shchehlov.dao.impl.ServiceDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Service;
import ua.kharkiv.epam.shchehlov.services.ServiceService;
import ua.kharkiv.epam.shchehlov.services.impl.ServiceServiceImpl;
import ua.kharkiv.epam.shchehlov.web.command.Command;
import ua.kharkiv.epam.shchehlov.web.command.CommandContainer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/controller")
public class FrontController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String PAGE_SERVICE_LIST = "/WEB-INF/jsp/serviceList.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        ServiceService serviceService = new ServiceServiceImpl(new ServiceDaoImpl());
        List<Service> serviceList = serviceService.getAll();
        session.setAttribute("serviceList", serviceList);
        req.setAttribute("serviceList", serviceList);
        System.out.println(serviceList);
//        return PAGE_SERVICE_LIST;

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(PAGE_SERVICE_LIST);
        dispatcher.forward(req,resp);
//        processRequest(req, resp);
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
//
        String forward = command.execute(req, resp);

        req.getRequestDispatcher(forward).forward(req, resp);
    }

}
